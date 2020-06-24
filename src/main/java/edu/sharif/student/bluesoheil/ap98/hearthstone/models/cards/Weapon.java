package edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards;

public class Weapon extends Card{
    private int attack, durability;

    Weapon(String name, int manaCost, Rarity rarity, HeroClass heroClass, String description
            , int cost, int attack , int durability) {
        super(name, manaCost, rarity, heroClass, description , cost);
        setType(CardType.WEAPON);
        setAttack(attack);
        setDurability(durability);
    }

//    public int getHP() {
//        return HP;
//    }
//
//    public void setHP(int HP) {
//        this.HP = HP;
//    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }
}
