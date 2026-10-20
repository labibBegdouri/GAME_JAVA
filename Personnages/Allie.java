package Personnages;
import SAC.*;
public class Allie extends Personnage {
    private Arme arme;
    private boolean trahison;

    public Allie(int pvInit,boolean trahison, Arme a){
        super("ALLIE",pvInit);
        this.trahison = trahison;
        this.arme = a;
    }

    public boolean getTrahison(){
        return this.trahison;
    }

    public Arme getArme(){
        return this.arme;
    }
}