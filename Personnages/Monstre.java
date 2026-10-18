package Personnages;
import SAC.*;

public class Monstre extends Personnage{
    private Arme arme;

    public Monstre(Arme a) {
        super("MONSTRE");
        this.arme = a;
    }

    public Arme getArme(){
        return this.arme;
    }

}