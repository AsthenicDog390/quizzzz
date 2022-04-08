package Services;

import commons.Activity;
import commons.questions.Estimate;
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

    Set<Integer> ids = new HashSet<>();

    List<Activity> recivedActivities; //for storing a list at least 60 different activities. This speeds up the generator process.

    //HashMap<Integer, Integer> temp = new HashMap<>();
    boolean firstRun = true;

    public QuestionBuilder(ActivityRepository repo) {
        this.repo = repo;
        this.recivedActivities = new ArrayList<>();
    }

    private void generateActivities() { //gets a set of activities to be used in a single game
        //getting at least 60 different activities with similar consumptions.
        long len = repo.count();
        long center = 0;
        while (center == 0) {
            long index = ThreadLocalRandom.current().nextLong(0, len);
            var maybeActivity = repo.findById(index);
            if (maybeActivity.isPresent()) {
                center = maybeActivity.get().getConsumptionInWh();
            }
        }
        long tenPercent = center / 10;
        long minn = center - 3 * tenPercent;
        long maxx = center + 3 * tenPercent;
        List<Activity> activities = new ArrayList<>();
        while (activities.size() < 60) {
            activities = repo.findActivitiesInRange(minn, maxx);
            minn /= 2;
            maxx *= 2;
        }
        this.recivedActivities = activities;
    }
    /*
    private List<Activity> generate3Activities(ActivityRepository repo){

        List<Activity> activities = new ArrayList<>();
        activities.add(repo.findById(id).get());
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

//    private List<Activity> generate3Activities(ActivityRepository repo){
//        long len = repo.count();
//        List<Activity> options = new ArrayList<>();
//        var id = (long) ThreadLocalRandom.current().nextInt(0, (int) len);
//        while(ids.contains(id)) {
//            id = (long) ThreadLocalRandom.current().nextInt(0, (int) len);
//        }
//        //got a random activity, whose consumption will be used as the center of the interval of consumptions
//        //from which the activities will be chosen
//        int center = (int)repo.findById(id).get().getConsumptionInWh();
//        //center
//        long minn = center - center/5;
//        //lower bound
//        long maxx = center + center/5;
//        //upper bound
//        List<Activity> activities = new ArrayList<>();
//        int count = 0;
//        while(count <= 3){
//            if (ids.size() == (len - (3-count) +1)) {
//                ids.clear();
//                //if there are less than the 3 activities available, they will be reset
//                //the formula above was made in order to not reset the activities if there are less than 3 available and
//                //the rest were already chosen
//            }
//            activities = repo.findActivitiesInRange(minn, maxx);
//            for(int i = 0; i < activities.size() ; i++){
//                Activity current = activities.get(i);
//                if(!ids.contains(current.getActivity_ID())){
//                   options.add(current);
//                   ids.add(current.getActivity_ID());
//                   count++;
//                }
//                //going through the interval to get the activities.
//            }
//            minn/=2;
//            maxx*=2;
//            //if not enough activities were found, the interval will get bigger
//        }
//        return options;

//        long len = repo.count();
//        List<Activity> options = new ArrayList<>();
//        while (options.size() < 3) {
//            long index = ThreadLocalRandom.current().nextLong(0, len);
//            var maybeActivity = repo.findById(index);
//            if (maybeActivity.isPresent()) {
//                options.add(maybeActivity.get());
//            }
//        }
//
//        return options;
//    }

    private List<Activity> generate3Activities() {//takes 3 activities from the list that were not previously used in the same game and returns them
        List<Activity> options = new ArrayList<>();
        long size = repo.count();
        while (options.size() < 3) {
            long random = ThreadLocalRandom.current().nextLong(0, size);
            var ac = this.repo.findById(random);
            if (ac.isPresent()) {
                options.add(ac.get());
            }
        }
        //System.out.println("Ids used " + temp);
        return options;
    }

    public MoreExpensive generateMoreExpensiveQuestion() {
        //runs the generateActivities functions if this is the first question generated by this instance of the builder
//        if (firstRun) {
//            firstRun = false;
//            generateActivities();
//        }
        List<Activity> activities = generate3Activities();
        Activity[] options = new Activity[3];
        Activity answer = activities.get(0);
        for (int i = 0; i <= 2; i++) {
            options[i] = activities.get(i);
            if (options[i].getConsumptionInWh() > answer.getConsumptionInWh()) {
                answer = options[i];
            }
        }
        MoreExpensive ans = new MoreExpensive(options, answer);
        return ans;
    }

    public LessExpensive generateLessExpensiveQuestion() {
        //runs the generateActivities functions if this is the first question generated by this instance of the builder
//        if (firstRun) {
//            firstRun = false;
//            generateActivities();
//        }
        List<Activity> activities = generate3Activities();
        Activity[] options = new Activity[3];
        Activity answer = activities.get(0);
        for (int i = 0; i <= 2; i++) {
            options[i] = activities.get(i);
            if (options[i].getConsumptionInWh() < answer.getConsumptionInWh()) {
                answer = options[i];
            }
        }
        LessExpensive ans = new LessExpensive(options, answer);
        return ans;
    }

    public Estimate genereateEstimateQuestion() {
        int random = ThreadLocalRandom.current().nextInt(0, recivedActivities.size());
        while (ids.contains(random)) {
            random = ThreadLocalRandom.current().nextInt(0, recivedActivities.size());
        }
        ids.add(random);
        Activity activity = recivedActivities.get(random);
        Estimate ans = new Estimate(activity);
        return ans;
    }
}
