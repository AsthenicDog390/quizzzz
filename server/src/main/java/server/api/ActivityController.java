package server.api;

import java.util.*;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final Random random;
    private final ActivityRepository repo;

    public ActivityController(Random random, ActivityRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    /**
     * getAll returns all activities in the database
     * @return a list containing all stored activities
     */
    @GetMapping(path = { "", "/" })
    public List<Activity> getAll() {
        return repo.findAll();
    }

    /**
     * getById gets an activity with the id id
     * @param id the id of the activity to get
     * @return a ResponseEntity containing the found activity or a bad request response if it does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(activityById(id));
    }

    /**
     * add adds a list of activities to the database
     * @param activities the list of activities to add
     * @return a ResponseEntity containing a list containing all activities that were added to the database
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<List<Activity>> add(@RequestBody List<Activity> activities) {

        for (Activity activity: activities) {
            if (isNullOrEmpty(activity.id) || isNullOrEmpty(activity.source)
                    || isNullOrEmpty(activity.title)) {
                return ResponseEntity.badRequest().build();
            }
        }

        List<Activity> saved = repo.saveAll(activities);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * getRandom is an endpoint which returns a single random activity
     * @return a random activity
     */
    @GetMapping("/random")
    public ResponseEntity<Activity> getRandom() {
        long len = repo.count();
        if (len < 1) {
            return ResponseEntity.badRequest().build();
        }
        var id = (long) randomInRange(1, (int) len);
        return ResponseEntity.ok(activityById(id));
    }

    /**
     * getNRandom is an endpoint which returns n random activities and only starts using duplicates once every activity has been used
     * @param num the amount of activities to return
     * @return a ResponseActivity holding the random activities
     */
    @GetMapping("/random/{num}")
    public ResponseEntity<List<Activity>> getNRandom(@PathVariable("num") int num) {
        long len = repo.count();
        if (len < 1) {
            return ResponseEntity.badRequest().build();
        }

        Set<Long> ids = new HashSet<>();
        List<Activity> activities = new ArrayList<>();

        for (int i = 0; i < num; i ++) {
            var id = (long) randomInRange(1, (int) len);
            while (ids.contains(id)) {
                id = randomInRange(1, (int) len);
            }
            ids.add(id);
            if (ids.size() == len) {
                ids.clear();
            }

            activities.add(activityById(id));
        }

        return ResponseEntity.ok(activities);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") int id) {
        if(repo.existsById((long) id)) {
            repo.deleteById((long) id);
            return "Activity deleted successfully!";
        }
        else return "Activity does not exist!";

    }

    private int randomInRange(int lower, int upper) {
        return random.nextInt(upper - lower + 1) + lower;
    }

    private Activity activityById(long id) {
        return repo.findById(id).get();
    }
}
