package edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes;

public class Warlock extends Hero{
    private static int initialHp=35;

    Warlock (){
        setHp(initialHp);
    }
    @Override
    public int getHp() {
        return hp;
    }

    @Override
    void runSpecialPower() {

    }
}
