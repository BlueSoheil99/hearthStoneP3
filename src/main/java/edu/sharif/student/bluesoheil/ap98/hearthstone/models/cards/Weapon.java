package edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards;

public class Weapon extends Card{

    Weapon(String name, int manaCost, Rarity rarity, HeroClass heroClass, String description
            , int cost, int attack , int durability) {
        super(name, manaCost, rarity, heroClass, description , cost);
        setType(CardType.WEAPON);
        setAttack(attack);
        setDurability(durability);

    }

}
