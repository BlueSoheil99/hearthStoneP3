package edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.LogicConstants;

import java.io.FileWriter;
import java.io.PrintWriter;

public class CardCreator{

    private static void saveCard(Card card) {
        try {
            String name = card.getName();
            FileWriter writer = new FileWriter(LogicConstants.getInstance().getCardsPath() + "/" + card.getName() + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrintWriter printer = new PrintWriter(writer);
            printer.println(gson.toJson(card));
            printer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //////////////////////////////////////////////////
        //phase 1 cards
        //////////////////////////////////////////////////
//        Spell FriendlySmith= new Spell("Friendly Smith" , 1 , Card.Rarity.COMMON , Card.HeroClass.ROGUE, "N/A",3 );
//        Spell Polymorph= new Spell("Polymorph" , 4 , Card.Rarity.RARE , Card.HeroClass.MAGE, "N/A" ,3);
//        Spell ArcaneExplosion= new Spell("Arcane explosion" , 2 , Card.Rarity.COMMON , Card.HeroClass.MAGE, "N/A"  , 3);
//        Beast Dreadscale= new Beast("Dreadscale" , 3 , Card.Rarity.LEGENDARY , Card.HeroClass.WARLOCK, "N/A",4 , 2,3);
//            //// new ones
//        Beast Mole= new Beast("Mole" , 5 , Card.Rarity.EPIC , Card.HeroClass.ROGUE, "Randomly attacks to one of enemy minions",4,5,4 );
//        Spell ConcreteShield= new Spell("Concrete Shield" , 4 , Card.Rarity.EPIC , Card.HeroClass.WARLOCK, "Adds 10 defence to hero",4 );
//        saveCard(FriendlySmith);
//        saveCard(Polymorph);
//        saveCard(ArcaneExplosion);
//        saveCard(Dreadscale);
//        saveCard(ConcreteShield);
//        saveCard(Mole);
//
//        Spell ShadowMadness = new Spell("Shadow Madness" , 3, Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",2 );//its for priest
//        Weapon SuperCollider  = new Weapon("Super Collider" , 5 , Card.Rarity.EPIC , Card.HeroClass.NEUTRAL, "N/A",2 ,1,3);//warrior's
//        Weapon Gorehowl  = new Weapon("Gorehowl" , 7 , Card.Rarity.EPIC , Card.HeroClass.NEUTRAL, "N/A",4 ,7,1); //for warrior actually
//        saveCard(ShadowMadness);
//        saveCard(SuperCollider);
//        saveCard(Gorehowl);
//
//        Minion Zilliax = new Minion("Zilliax" , 5 , Card.Rarity.LEGENDARY , Card.HeroClass.NEUTRAL, "N/A",3 , 2,2);
//        Minion Abomination = new Minion("Abomination" , 4 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",4,4,3 );
//        Minion OmegaMedic  = new Minion("Omega Medic" , 3 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",3 , 4,2);
//        Minion MurlocTidehunter= new Minion("Murloc Tidehunter" , 2 , Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A" ,2,1,1);
//        Minion Waterboy = new Minion("Waterboy" , 2 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A" ,2,1,2);
//        Minion VoodooDoctor = new Minion("Voodoo doctor" , 1 , Card.Rarity.COMMON, Card.HeroClass.NEUTRAL, "N/A",2,1,2 );
//        saveCard(Zilliax);
//        saveCard(Abomination);
//        saveCard(OmegaMedic);
//        saveCard(MurlocTidehunter);
//        saveCard(Waterboy);
//        saveCard(VoodooDoctor);
//
//        Spell Consecration = new Spell("Consecration" , 4 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",2 );
        Weapon Stormhammer = new Weapon("Stormhammer" , 3 , Card.Rarity.EPIC , Card.HeroClass.NEUTRAL, "N/A",4 ,3,2);
//        Spell Slam  = new Spell("Slam" , 2 , Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",1);
//        Spell Humility   = new Spell("Humility" , 1 , Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",2);
//        Spell Innervate = new Spell("Innervate" , 0 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A"  , 2);
//        saveCard(Consecration);
        saveCard(Stormhammer);
//        saveCard(Slam);
//        saveCard(Humility);
//        saveCard(Innervate);

        //////////////////////////////////////////////////
        //phase 2 cards
        //////////////////////////////////////////////////
//        Minion c1= new Minion("Sathrovarr" , 9 , Card.Rarity.LEGENDARY , Card.HeroClass.NEUTRAL, "N/A",5 , 5,6);
//        Minion c2= new Minion("Security Rover" , 6 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",2 , 6,3);//warrior actually
//        Minion c3= new Minion("Curio Collector" , 5 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",4 , 4,3);
//        Minion c4= new Minion("Tomb Warden" , 8 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",3 , 6,4);//warrior actually
//        Spell c5= new Spell("Sprint" , 7 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",4 );//its for rogue actually
//        Spell c6= new Spell("Swarm of Locusts" , 6 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",4 ); //hunter actually
//        Spell c7= new Spell("Pharaoh's Blessing" , 6 , Card.Rarity.RARE , Card.HeroClass.NEUTRAL, "N/A",4 );//paladin actually
//        Spell c8= new Spell("Book of Specters" , 2 , Card.Rarity.EPIC , Card.HeroClass.NEUTRAL, "N/A",4 );
//        QuestAndReward c9= new QuestAndReward("Strength in Numbers" , 1 , Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",2);//its not really neutral
//        QuestAndReward c10= new QuestAndReward("Learn Draconic" , 1, Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",2 );// its for mage actually
//        Weapon c11= new Weapon("Heavy Axe" , 1, Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",2,1,3 );// its for warrior actually
//        Weapon c12= new Weapon("Mirage Blade" , 2, Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",2 ,3,2);// its for rogue actually..its not common
//        Weapon c13= new Weapon("Dragon Claw" , 5, Card.Rarity.COMMON , Card.HeroClass.NEUTRAL, "N/A",2 ,5,2);// its rarity is unknown
//        saveCard(c1);
//        saveCard(c2);
//        saveCard(c3);
//        saveCard(c4);
//        saveCard(c5);
//        saveCard(c6);
//        saveCard(c7);
//        saveCard(c8);
//        saveCard(c9);
//        saveCard(c10);
//        saveCard(c11);
//        saveCard(c12);
//        saveCard(c13);
//
//        Beast c14= new Beast("Swamp King Dred" , 7 , Card.Rarity.LEGENDARY , Card.HeroClass.HUNTER, "N/A",9 , 9,10);
//        Minion c15= new Minion("High Priest Amet" , 4 , Card.Rarity.LEGENDARY , Card.HeroClass.PRIEST, "N/A",2 , 7,8);
//        saveCard(c14);
//        saveCard(c15);
//        Spell c16= new Spell("Covid" , 4 , Card.Rarity.RARE , Card.HeroClass.PRIEST, "kills all minions with 1 or 2 hp",9);
//        Beast c17= new Beast("Eagle" , 4 , Card.Rarity.EPIC , Card.HeroClass.HUNTER, "Attacks opponent beasts at the start of your turn",4, 5,8);
//        saveCard(c16);
//        saveCard(c17);

    }
}
