package edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroPowers.HeroPower;

public abstract class Hero {
    private static int initialHp=30;
    protected Deck deck;
    protected HeroPower heroPower;
    protected int hp;

    Hero(){
        setHp(initialHp);
    }
    abstract void runSpecialPower();


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
