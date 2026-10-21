package MAPS;

import java.util.Objects;
/**
 * Position bidimensionnelle exprimée par des coordonnées x et y.
 */

public class Position {
    private int x;
    private int y;
    //private int z; //Etage

    /**
     * Crée un objet Position avec les paramètres fournis.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
        //this.z = z;
    }

    /**
     * Retourne la coordonnée horizontale.
     * @return la coordonnée horizontale.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Retourne la coordonnée verticale.
     * @return la coordonnée verticale.
     */
    public int getY(){
        return this.y;
    }

    //public int getZ(){
      //  return this.z;
    //}

    /**
     * Indique si cet objet représente la même position ou le même objet que la valeur fournie.
     * @param other objet à comparer
     * @return {@code true} si l’objet fourni est égal à celui-ci, sinon {@code false}.
     */
    public boolean equals(Object other){
        if (other instanceof Position){
            Position o = (Position) other;
            return this.x == o.getX() && 
                   this.y == o.getY() ;
                    //this.z == o.getZ();
        }
        return false;
    }

    /**
     * Calcule le code de hachage de cet objet.
     * @return le code de hachage de l’objet.
     */
    public int hashCode(){  //A rediscuter hh
        return Objects.hash(this.x, this.y);
    }

    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}