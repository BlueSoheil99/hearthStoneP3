package edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroPowers;

public abstract class HeroPower {
    //todo complete hero powes.........maybe we should have heropower as an interface
    private String description;

    HeroPower(String description){
        this.description = description;
    }

    public abstract void runPower();
}
