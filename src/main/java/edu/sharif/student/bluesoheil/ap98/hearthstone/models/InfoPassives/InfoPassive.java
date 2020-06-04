package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

public enum  InfoPassive {
    MANAJUMP("ManaJump","You'll have one extra mana \nuntil you get to 10th turn" ,new ManaJump()),
    NURSE("nurse", "At the end of each turn,\nrestores a random minion HP" , new Nurse()),
    OFFCARDS("OffCards","Every card will cost\n 1 less mana ", new OffCards()),
    TWICEDRAW("TwiceDraw","In each turn you can \nhave 2 cards instead of one", new TwiceDraw()),
    WARRIORS ("Warriors" , "If a summoned minion dies,\n hero gets 2 defence", new Warriors());

    private String name;
    private String description;
    private Passive passive;

     InfoPassive(String name , String description, Passive passive){
        this.name = name;
        this.description = description;
        this.passive = passive;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Passive getPassive() {
        return passive;
    }
}
