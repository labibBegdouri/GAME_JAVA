package SAC;

import java.util.Objects;

import MAPS.*;
/**
 * Classe de base des objets du jeu, avec un nom, un identifiant, une taille et une position.
 */

public class  Item{
    private String nom;
    private int id; // or string ????
    private int size;
    private Position pos;

    /**
     * Retourne la position de l’objet ou de la porte.
     * @return la position de l’objet, de la porte ou du personnage.
     */
    public Position getPosition(){
        return this.pos;
    }


    /**
     * Définit la position à partir des coordonnées fournies.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public void setPosition(int x,int y){
        this.pos= new Position(x,y);
    }

    /**
     * Crée un objet Item avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param id identifiant de l’élément
     * @param size taille de l’élément
     */
    public  Item(String nom,int id,int size){
        this.nom=nom;
        this.id=id;
        this.size=size;
    }

    /**
     * Crée un objet Item avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param id identifiant de l’élément
     */
    public  Item(String nom,int id){
        this.nom=nom;
        this.id=id;
        this.size=1;
    }

    /**
     * Retourne le nom de l’objet.
     * @return le nom du personnage ou de l’objet.
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Définit le nom de l’objet.
     * @param nom nom de l’élément
     */
    public void setNom(String nom){
        this.nom=nom;
    }

    /**
     * Retourne l’identifiant de l’objet.
     * @return l’identifiant de l’objet.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Définit l’identifiant de l’objet.
     * @param id identifiant de l’élément
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Retourne la taille de l’objet.
     * @return la taille de l’objet.
     */
    public int getSize(){
        return this.size;
    }
    /**
     * Définit la taille de l’objet.
     * @param size taille de l’élément
     */
    public void setSize(int size ){
        this.size=size;
    }

    /**
     * Indique si cet objet représente la même position ou le même objet que la valeur fournie.
     * @param obj objet à comparer
     * @return {@code true} si l’objet fourni est égal à celui-ci, sinon {@code false}.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Item)) {
            return false;
        }
        Item other = (Item) obj;
        return this.id == other.id && Objects.equals(this.nom, other.nom);
    }

    /**
     * Calcule le code de hachage de cet objet.
     * @return le code de hachage de l’objet.
     */
    public int hashCode() {
        return Objects.hash(this.id, this.nom);
    }

    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString() {
        return "{ Nom = " + this.nom + ", Type = Objet, Taille = " + this.size + " }";
    }




}