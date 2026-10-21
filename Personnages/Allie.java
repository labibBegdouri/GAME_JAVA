package Personnages;
import SAC.*;
/**
 * Allié du héros, doté de points de vie, d’une arme et d’un éventuel comportement traître.
 */
public class Allie extends Personnage {
    private static int compteur = 0;
    private Arme arme;
    private boolean trahison;

    /**
     * Crée un objet Allie avec les paramètres fournis.
     * @param pvInit points de vie initiaux
     * @param trahison valeur utilisée par la méthode
     * @param a arme utilisée
     */
    public Allie(int pvInit,boolean trahison, Arme a){
        super("Allie " + (++compteur), pvInit);
        this.trahison = trahison;
        this.arme = a;
    }

    public static void resetCompteur() {
        compteur = 0;
    }

    /**
     * Indique si l’allié trahit le héros.
     * @return {@code true} si l’allié trahit le héros, sinon {@code false}.
     */
    public boolean getTrahison(){
        return this.trahison;
    }

    /**
     * Retourne l’arme équipée.
     * @return l’arme équipée.
     */
    public Arme getArme(){
        return this.arme;
    }
}