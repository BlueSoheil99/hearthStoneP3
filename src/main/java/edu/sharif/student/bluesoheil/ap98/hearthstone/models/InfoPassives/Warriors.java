package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

public class Warriors extends Passive{
    public static Warriors instance;

    Warriors() {

    }
    public static Warriors getInstance() {
        if (instance == null) instance = new Warriors();
        return instance;
    }
    @Override
    public void run() {

    }
}
