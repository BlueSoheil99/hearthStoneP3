package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.collection;

import java.util.EventObject;

public class FilterEvent extends EventObject {

    private String regex;
    private boolean owned, notOwned;
    private int manaCost;
    private String hero;

    public FilterEvent(Object source , String regex , boolean owned , boolean notOwned , int manaCost , String hero) {
        super(source);
        this.regex = regex;
        this.owned = owned;
        this.notOwned = notOwned;
        this.manaCost = manaCost;
        this.hero = hero;
    }

    public String getRegex() {
        return regex;
    }

    public boolean isOwned() {
        return owned;
    }

    public boolean isNotOwned() {
        return notOwned;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getHero() {
        return hero;
    }
}
