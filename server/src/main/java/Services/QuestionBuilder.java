package Services;

import commons.Activity;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionBuilder {

    ActivityRepository repo;
    String gameID;
    Set<Long> ids = new HashSet<>();

    public QuestionBuilder(ActivityRepository repo, String gameID) {
        this.repo = repo;
        this.gameID = gameID;
    }
    /*
    private List<Activity> generate3Activities(ActivityRepository repo){
        long len = repo.count();
        var id = (long) ThreadLocalRandom.current().nextInt(0, (int)len);
        List<Activity> activities = new ArrayList<>();
        activities.add(repo.getById(id));
        Long minn = activities.get(0).getConsumptionInWh();
        minn = minn - minn/5;
        Long maxx = activities.get(0).getConsumptionInWh();
        maxx = maxx + maxx/5;
        List<Activity> options = repo.findActivitiesInRange(minn, maxx);
        while(options.size() <= 3){
            options = repo.findActivitiesInRange(minn/2, maxx*2);
        }
        int random = ThreadLocalRandom.current().nextInt(0, options.size());
        activities.add(options.get(random));
        random = ThreadLocalRandom.current().nextInt(0, options.size());
        activities.add(options.get(random));
        while(activities.get(0).equals(activities.get(1)) || activities.get(0).getConsumptionInWh() == activities.get(1).getConsumptionInWh()){
            random = ThreadLocalRandom.current().nextInt(0, options.size());
            activities.set(1, options.get(random));
        }
        while(activities.get(0).equals(activities.get(2)) || activities.get(1).equals(activities.get(2)) || activities.get(0).getConsumptionInWh() == activities.get(2).getConsumptionInWh() || activities.get(1).getConsumptionInWh() == activities.get(2).getConsumptionInWh()){
            random = ThreadLocalRandom.current().nextInt(0, options.size());
            activities.set(2, options.get(random));
        }
        return activities;
    }
     */

    private List<Activity> generate3Activities(ActivityRepository repo){
        long len = repo.count();
        List<Activity> options = new ArrayList<>();
        var id = (long) ThreadLocalRandom.current().nextInt(0, (int) len);
        while(ids.contains(id)){
            id = (long) ThreadLocalRandom.current().nextInt(0, (int) len);
        }
        int center = (int)repo.getById(id).getConsumptionInWh();
        long minn = center - center/5;
        long maxx = center + center/5;
        List<Activity> activities = new ArrayList<>();
        int count = 0;
        while(count <= 3){
            if (ids.size() == (len - (3-count) +1)) {
                ids.clear();
            }
            activities = repo.findActivitiesInRange(minn, maxx);
            for(int i = 0; i <= activities.size() ; i++){
                Activity current = activities.get(i);
                if(!ids.contains(current.getActivity_ID())){
                   options.add(current);
                   ids.add(current.getActivity_ID());
                   count++;
                }
            }
            minn/=2;
            maxx*=2;
        }
        return options;
    }

    public MoreExpensive generateMoreExpensiveQuestion()
    {
        List<Activity> activities = generate3Activities(repo);
        Activity[] options = new Activity[3];
        Activity answer = activities.get(0);
        for(int i = 0 ; i <= 2; i++){
            options[i] = activities.get(i);
            if(options[i].getConsumptionInWh() > answer.getConsumptionInWh()){
                answer = options[i];
            }
        }
        MoreExpensive ans = new MoreExpensive(options, answer);
        return ans;
    }

    public LessExpensive generateLessExpensiveQuestion()
    {
        List<Activity> activities = generate3Activities(repo);
        Activity[] options = new Activity[3];
        Activity answer = activities.get(0);
        for(int i = 0 ; i <= 2; i++){
            options[i] = activities.get(i);
            if(options[i].getConsumptionInWh() < answer.getConsumptionInWh()){
                answer = options[i];
            }
        }
        LessExpensive ans = new LessExpensive(options, answer);
        return ans;
    }
}
