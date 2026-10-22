package Personnages;

import SAC.*;
import Position.Position;
/**
 * Base commune aux personnages, qui définit leur identité, leurs points de vie et leur position.
 */
public abstract class Personnage {
    private String nom;
    private int pvInit;
    private int pv;
    private Position pos;
    
    /**
     * Retourne les points de vie initiaux du personnage.
     * @return les points de vie initiaux du personnage.
     */
    public int getPvInit() {
        return this.pvInit;
    }
    /**
     * Définit le nom de l’objet.
     * @param nom nom de l’élément
     */
    public void setNom(String nom){
        this.nom = nom;
    }

    /**
     * Retourne le nom de l’objet.
     * @return le nom du personnage.
     */
    public String getNom(){
        return this.nom;
    }
    /**
     * Retourne la position de l’objet ou de la porte.
     * @return la position du personnage.
     */
    public Position getPosition(){
        return this.pos;
    }
    /**
     * Définit la position à partir des coordonnées fournies.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public void setPosition(int x, int y){
        this.pos = new Position(x, y);
    }
    /**
     * Retourne les points de vie actuels du personnage.
     * @return les points de vie actuels du personnage.
     */
    public int getPv(){
        return this.pv;
    }

    /**
     * Définit les points de vie sans dépasser leur valeur initiale ni descendre sous zéro.
     * @param pv points de vie
     */
    public void setPv(int pv) {
        // tu ne peux pas depasser le pv initialement fixe
        if (pv < pvInit) {
            this.pv = Math.max(0, pv);
        } else {
            this.pv = pvInit;
        }
    }

    /**
     * Indique si le personnage n’a plus de points de vie.
     * @return {@code true} si le personnage n’a plus de points de vie, sinon {@code false}.
     */
    public boolean getEstMort() {
        return (this.pv <= 0);
    }


    /**
     * Crée un objet Personnage avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param pvInit points de vie initiaux
     */
    public Personnage(String nom, int pvInit) {
        this.nom = nom;
        this.pv = pvInit;
        this.pvInit = pvInit; 
    }

    /**
     * Attaque le personnage ciblé avec l’arme fournie.
     * @param victime personnage ciblé
     * @param a arme utilisée
     */
    public void attaquer(Personnage victime, Arme a) {
        if (!victime.getEstMort() && a!=null){
            victime.setPv(victime.getPv() - a.getDegat());
        }
    }
}






