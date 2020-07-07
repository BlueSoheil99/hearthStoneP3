package edu.sharif.student.bluesoheil.ap98.hearthstone.util;

import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.TimerListener;

public class PlayTimer extends Thread {
    private static PlayTimer instance;
    private int period;
    private int remainingTime;
    private TimerListener timerListener;

    private PlayTimer(int period) {
        this.period = period;
        reset();
    }

    public static PlayTimer setNewTimer(int period){
        instance = new PlayTimer(period);
        return instance;
    }
    public static PlayTimer getCurrentTimer(){
        return instance;
    }
    public void stopTimer() {
        System.out.println("timer stopped");
        this.stop();
    }


    private void reset() {
        remainingTime = period;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setTimeListener(TimerListener timeListener) {
        this.timerListener = timeListener;
    }

    @Override
    public void run() {
        while (! isInterrupted()) {
            if (timerListener != null) timerListener.tick();
            try {
                sleep(1000);
                remainingTime -= 1;
                if (remainingTime == 0) {
                    if (timerListener != null) timerListener.ring();
                    reset();
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
