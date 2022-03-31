package server.services;

import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class TimerService {
    public void runAfter(int seconds, Runnable function) {
        var t = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                function.run();
            }
        };
        t.schedule(timerTask,seconds*1000) ;
    }
}
