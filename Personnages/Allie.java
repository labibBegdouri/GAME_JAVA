package Personnages;
import SAC.*;
public class Allie extends Creature {
    private boolean trahison;

    public Allie(int pvInit, boolean trahison, Arme a){
        super(pvInit, a);
        this.trahison = trahison;
    }
}