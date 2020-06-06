package edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards;

public class Minion extends Card{

    private boolean inRush;
    private int HP,attack;

    Minion(String name, int manaCost, Rarity rarity,HeroClass heroClass, String description , int attack , int hp , int cost) {
        super(name, manaCost, rarity, heroClass, description , cost);
        setHP(hp);
        setAttack(attack);
        setType(CardType.MINION);
        inRush =false;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setRush(boolean inRush) {
        this.inRush = inRush;
    }

    public boolean isInRush() {
        return inRush;
    }

}
