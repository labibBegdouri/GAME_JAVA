package Personnages;
import SAC.*;
/**
 * Monstre équipé d’une arme et utilisant les points de vie communs définis pour les monstres.
 */

public class Monstre extends Personnage{
    private static int pvCommuns = 50;
    private Arme arme;

    /**
     * Définit les points de vie initiaux communs aux monstres.
     * @param pv points de vie
     */
    public static void setPvCommuns(int pv) {
        pvCommuns = pv;
    }

    /**
     * Retourne les points de vie initiaux communs aux monstres.
     * @return les points de vie initiaux communs aux monstres.
     */
    public static int getPvCommuns() {
        return pvCommuns;
    }

    /**
     * Crée un objet Monstre avec les paramètres fournis.
     * @param a arme utilisée
     */
    public Monstre(Arme a) {
        super("MONSTRE", pvCommuns);
        this.arme = a;
    }
    
    /**
     * Retourne l’arme équipée.
     * @return l’arme équipée.
     */
    public Arme getArme(){
        return this.arme;
    }

}