package client.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import commons.ImageUpload;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ActivityUtils {
    private static final String SERVER = "http://localhost:8080/";

    /**
     * Reader made for parsing the activities from the JSON file given.
     * @param input - reader that parses through the file.
     * @return - a list containing all the activities.
     */
    public List<Activity> readActivities(Reader input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Activity> activities = Arrays.asList(mapper.readValue(input, Activity[].class));
            return activities;
        } catch (JsonParseException e) {
            throw new RuntimeException("could not parse json", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("could not map json", e);
        } catch (IOException e) {
            throw new RuntimeException("could not read file", e);
        }
    }

    /**
     * Validation for activities from a zip file.
     * @param activities the list of activities given.
     * @param file the ZipFile given.
     */
    private void validateActivities(List<Activity> activities, ZipFile file) {
        for (Activity a: activities) {
            if (a.getSource().length() > 450) {
                throw new RuntimeException(String.format("activity too long to fit in database: %s", a.getId()));
            }
            ZipEntry entry = file.getEntry(a.getImagePath());
            if (entry == null) {
                throw new RuntimeException(String.format("activity image file does not exist, activity: %s, image file: %s", a.getId(), a.getImagePath()));
            }
        }
    }

    /**
     * Adding activities using an API endpoint to the list by returning a new list of activities.
     * @param activities - the list to activities to add the activity.
     * @return - the new activities list with added activities.
     */
    public List<Activity> addActivities(List<Activity> activities) {
        var res = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .buildPost(Entity.entity(activities, APPLICATION_JSON))
                .invoke();
        if (res.getStatusInfo() != Response.Status.OK) {
            throw new RuntimeException("could not post activities");
        }
        return res.readEntity(new GenericType<List<Activity>>() {});
    }

    /**
     * Adding activities images to an activities array from a specific ZipFil given.
     * @param activities - the activities array where we'll add the images.
     * @param file - the file from where we'll get the activites.
     */
    public void addActivityImages(List<Activity> activities, ZipFile file) {
        for (Activity a: activities) {
            try {
                var entry = file.getEntry(a.getImagePath());
                var stream = file.getInputStream(entry);

                var img = new ImageUpload(stream.readAllBytes());

                var res = ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("api/images")
                        .path(a.getImagePath())
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .put(Entity.entity(img, APPLICATION_JSON));

                if (res.getStatusInfo() != Response.Status.OK) {
                    throw new RuntimeException("error code when uploading file: " + res.getStatusInfo().toString());
                }

            } catch (IOException e) {
                throw new RuntimeException("could not upload image", e);
            }
        }
    }

    /**
     * Adding activities from a zip filed into the repository, binding all methods described upwards
     * ("validateActivities", "addActivities", "addActivityImages").
     * @param path - the path of the ZipFile.
     */
    public void addActivitiesFromZipFile(String path) {
        try (ZipFile file = new ZipFile(path)) {
            var activitiesEntry = file.getEntry("activities.json");
            if (activitiesEntry == null) {
                throw new IllegalArgumentException("provided zip file does not have");
            }
            var activities = readActivities(new InputStreamReader(file.getInputStream(activitiesEntry)));
            validateActivities(activities, file);
            addActivities(activities);
            addActivityImages(activities, file);
        } catch (IOException e) {
            throw new RuntimeException("could not read zip file", e);
        }
    }
}
