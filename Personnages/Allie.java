package Personnages;
import SAC.*;
public class Allie extends Creature {
    private boolean trahison;

    public Allie(boolean trahison, Arme a){
        super(a);
        super.setNom("ALLIE");
        this.trahison = trahison;
    }
}