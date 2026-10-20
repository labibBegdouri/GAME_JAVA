package Personnages;
import SAC.*;

public class Monstre extends Personnage{
    private static int pvCommuns = 50;
    private Arme arme;

    public static void setPvCommuns(int pv) {
        pvCommuns = pv;
    }

    public static int getPvCommuns() {
        return pvCommuns;
    }

    public Monstre(Arme a) {
        super("MONSTRE", pvCommuns);
        this.arme = a;
    }
    
    public Arme getArme(){
        return this.arme;
    }

}