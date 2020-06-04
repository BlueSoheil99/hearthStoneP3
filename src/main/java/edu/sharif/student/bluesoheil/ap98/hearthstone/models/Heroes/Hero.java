package edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroPowers.HeroPower;

public abstract class Hero {
    private Deck deck;
    private HeroPower heroPower;


    abstract void runSpecialPower();



}
