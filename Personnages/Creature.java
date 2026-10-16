package Personnages;
import SAC.*;

public class Creature extends Personnage{
    private Arme arme;

    public Creature(int pvInit, Arme a) {
        super(pvInit);
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