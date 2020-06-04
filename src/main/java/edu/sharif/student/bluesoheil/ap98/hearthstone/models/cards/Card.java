package edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards;

public  class Card {
    enum Rarity  {COMMON , RARE, EPIC , LEGENDARY}
    public enum HeroClass { MAGE , ROGUE , WARLOCK ,PRIEST,HUNTER, NEUTRAL}
    public enum CardType { SPELL , MINION , WEAPON , QUESTANDREWARD , BEAST}

    //properties
    private String name;
    private int manaCost;
    private CardType type ;
    private HeroClass heroClass;
    private Rarity rarity;
    private String description;
    private int cost;
    private int HP,attack, durability;

    public Card(String name , int manaCost , Rarity rarity, HeroClass heroClass , String description, int cost){
        this.name = name;
        this.heroClass = heroClass;
        this.rarity = rarity;
        this.description = description;
        this.cost = cost;
        // we don't need setters for methods above
        setManaCost(manaCost);
//        CardController.getInstance().saveCard(this);
    }

    //todo delete methods that only use here , for example i THINK setManaCost is not necessary

    public String getName() {
        return name;
    }
    public int getManaCost() {
        return manaCost;
    }
    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public CardType getType() {
        return type;
    }
     void setType(CardType type){ this.type = type;}
    public HeroClass getHeroClass() {
        return heroClass;
    }
    public Rarity getRarity() {
        return rarity;
    }
    public String getDescription() {
        return description;
    }
    public int getCost() {
        return cost;
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

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public String toString() {
        return name;
    }
}
