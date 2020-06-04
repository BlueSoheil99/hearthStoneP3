package edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes;

public enum  HeroTypes {
    MAGE("Mage" , new Mage()),
    WARLOCK("Warlock" , new Warlock()),
    ROGUE("Rogue" , new Rogue()),
    PRIEST("Priest" , new Priest()),
    HUNTER("Hunter" , new Hunter());

    private Hero hero;
    private String name;
    HeroTypes(String name , Hero hero){
        this.name = name;
        this.hero = hero;
    }
    public String getName(){
        return name;
    }
    public Hero getHero(){
        return hero;
    }
}
