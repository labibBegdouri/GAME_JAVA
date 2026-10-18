package Personnages;
import SAC.*;
public class Allie extends Personnage {
    private Arme a;
    private boolean trahison;

    public Allie(boolean trahison, Arme a){
        super("ALLIE");
        this.trahison = trahison;
        this.a = a;
    }

    public boolean getTrahison(){
        return this.trahison;
    }
}