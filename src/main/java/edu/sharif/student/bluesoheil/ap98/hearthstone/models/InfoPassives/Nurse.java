package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

public class Nurse extends Passive {
    private static Nurse instance;

    Nurse() {
    }

    public static Nurse getInstance() {
        if (instance == null) instance = new Nurse();
        return instance;
    }
    @Override
    public void run() {

    }
}
