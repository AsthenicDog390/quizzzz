package client.utils;

import commons.Activity;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;

import java.util.List;

public class QuestionGeneratorUtils {

    private static List<Activity> generate3Activities(ServerUtils server){
        List<Activity> activities = server.getNRandomActivities(3);
        //System.out.println( activities );
        Long minn = activities.get(0).getConsumptionInWh();
        minn = minn - minn/5;
        Long maxx = activities.get(0).getConsumptionInWh();
        maxx = maxx + maxx/5;
        while(activities.get(0).equals(activities.get(1)) || activities.get(1).getConsumptionInWh() > maxx || activities.get(1).getConsumptionInWh() < minn){
            activities.set(1, server.getRandomActivity());
        }
        while(activities.get(0).equals(activities.get(2)) || activities.get(1).equals(activities.get(2)) || activities.get(2).getConsumptionInWh() > maxx || activities.get(2).getConsumptionInWh() < minn){
            activities.set(2, server.getRandomActivity());
        }
        return activities;
    }

    /**
     * Helper method for generation a "What is more expensive" type of question.
     * @param server - the server from where it is generated.
     * @return - the freshly new generated question.
     */
    public static MoreExpensive generateMoreExpensiveQuestion(ServerUtils server) {
        List<Activity> activities = generate3Activities(server);
        Activity[] options = new Activity[3];
        Activity answer = activities.get(0);
        for(int i = 0 ; i <= 2; i++){
            options[i] = activities.get(i);
            if(options[i].getConsumptionInWh() > answer.getConsumptionInWh()){
                answer = options[i];
            }
        }
        MoreExpensive ans = new MoreExpensive(options, answer);
        //System.out.println(ans.toString());
        return ans;
    }

    /**
     * Helper method for generation a "What is less expensive" type of question.
     * @param server - the server from where it is generated.
     * @return - the freshly new generated question.
     */
    public static LessExpensive generateLessExpensiveQuestion(ServerUtils server) {
        List<Activity> activities = generate3Activities(server);
        Activity[] options = new Activity[3];
        Activity answer = activities.get(0);
        for(int i = 0 ; i <= 2; i++){
            options[i] = activities.get(i);
            if(options[i].getConsumptionInWh() < answer.getConsumptionInWh()){
                answer = options[i];
            }
        }
        LessExpensive ans = new LessExpensive(options, answer);
        //System.out.println(ans.toString());
        return ans;
    }
}
