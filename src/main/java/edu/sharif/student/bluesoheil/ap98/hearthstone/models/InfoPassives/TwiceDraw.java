package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

public class TwiceDraw extends Passive {
    public static TwiceDraw instance;

    TwiceDraw() {
    }
    public static TwiceDraw getInstance() {
        if (instance == null) instance = new TwiceDraw();
        return instance;
    }

    @Override
    public void run() {

    }
}
