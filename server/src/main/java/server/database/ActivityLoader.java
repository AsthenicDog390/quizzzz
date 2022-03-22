package server.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class ActivityLoader {

    @Bean
    ApplicationRunner initialiseRepo(ActivityRepository repo)
    {
        boolean run = false;
        //change this value to true in order to autorun the saveToRepo at the start of the server
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                if(run)
                saveToRepo(repo);
            }
        };
    }

    public void saveToRepo(ActivityRepository repo)
    {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Activity>> mapType = new TypeReference<List<Activity>>() {};
        InputStream is = TypeReference.class.getResourceAsStream("/oopp-activity-bank/activities.json");
        try {
            List<Activity> activityList = mapper.readValue(is, mapType);
            repo.saveAll(activityList);
            int actSize = activityList.size();
            System.out.println(actSize + " activities saved succesfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
