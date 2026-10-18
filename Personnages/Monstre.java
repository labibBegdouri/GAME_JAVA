package Personnages;
import SAC.*;

public class Monstre extends Personnage{
    private Arme arme;

    public Monstre(Arme a) {
        super("MONSTRE");
        this.arme = a;
    }

    public void attaquer(Personnage victime) {
        if (!victime.getEstMort()){
            Arme a = this.arme;
            if (victime.getPv() <= a.getDegat()){
                victime.setPv(0);
                victime.setEstMort(true);
            } else {
                victime.setPv(victime.getPv() - a.getDegat());
            }
        }
    }
}