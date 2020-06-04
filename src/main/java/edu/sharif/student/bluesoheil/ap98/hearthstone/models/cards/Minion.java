package edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards;

public class Minion extends Card{

    private boolean isInRush;
//    private int HP;
//    private int attack;

    Minion(String name, int manaCost, Rarity rarity,HeroClass heroClass, String description , int attack , int hp , int cost) {
        super(name, manaCost, rarity, heroClass, description , cost);
        this.
        setHP(hp);
        setAttack(attack);
        setType(CardType.MINION);
        isInRush=false;
    }

    public void setInRush(boolean inRush) {
        isInRush = inRush;
    }
}
