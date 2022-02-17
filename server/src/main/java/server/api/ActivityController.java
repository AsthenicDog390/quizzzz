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

    @GetMapping(path = { "", "/" })
    public List<Activity> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(activityById(id));
    }

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

    @GetMapping("/random")
    public ResponseEntity<Activity> getRandom() {
        long len = repo.count();
        if (len < 1) {
            return ResponseEntity.badRequest().build();
        }
        var id = (long) randomInRange(1, (int) len);;
        return ResponseEntity.ok(activityById(id));
    }

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

    private int randomInRange(int lower, int upper) {
        return random.nextInt((int) upper - lower + 1) + lower;
    }

    private Activity activityById(long id) {
        return repo.findById(id).get();
    }
}
