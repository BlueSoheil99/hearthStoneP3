package edu.sharif.student.bluesoheil.ap98.hearthstone.util;

import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.TimerListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlayTimer extends Thread {
    private static PlayTimer instance;
    private int period;
    private int remainingTime;
    private AtomicBoolean ticking = new AtomicBoolean(false);
    private TimerListener timerListener;

    //this class is singleton so playHandler could access it and stop it(at matchForfeit method)
    private PlayTimer(int period) {
        this.period = period;
        reset();
        remainingTime = period;
    }

    public static PlayTimer setNewTimer(int period) {
        instance = new PlayTimer(period);
        return instance;
    }

    public static PlayTimer setNewTimer() {
        return setNewTimer(PlayLogicConfig.getInstance().getDefaultTimerPeriod());
    }

    public static PlayTimer getCurrentTimer() {
        return instance;
    }

    public void stopTimer() {
        ticking.set(false);
        reset();
    }

    public void reset() {
        remainingTime = period;
        interrupt();
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setTimeListener(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    @Override
    public void run() {
        ticking.set(true);
        System.out.println("timer started");
        while (ticking.get()) try {
            while (!interrupted()) {
                if (timerListener != null) timerListener.tick();
                sleep(1000);
                remainingTime -= 1;
                if (remainingTime == 0) {
                    if (timerListener != null) timerListener.ring();
                    reset();
                }
            }
        } catch (InterruptedException ignored) {
        }
        System.out.println("timer stopped");
    }
}


