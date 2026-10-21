package MAPS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import SAC.*;
import Personnages.*;
/**
 * Salle contenant des objets, des personnages et des portes.
 */

public class Salle{
    private String nom;
    private ArrayList<Item> objetParSol;
    private ArrayList<Personnage> personnages;
    private Map<Direction, Porte> portes;
    private Position posInit;
    private int hauteur;
    private int largeur;
    
    /**
     * Retourne la hauteur de la salle.
     * @return la hauteur de la salle.
     */
    public  int getH(){
        return hauteur;
    }

    /**
     * Définit la hauteur de la salle.
     * @param h valeur utilisée par la méthode
     */
    public  void setH(int h){
        this.hauteur = h;
    }

    /**
     * Retourne la largeur de la salle.
     * @return la largeur de la salle.
     */
    public  int getL(){
        return largeur;
    }

    /**
     * Définit la largeur de la salle.
     * @param l valeur utilisée par la méthode
     */
    public  void setL(int l){
        this.largeur = l;
    }

    /**
     * Définit les coordonnées initiales de la salle.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public void setPosInit(int x, int y){
        this.posInit = new Position(x, y);
    }
    /**
     * Retourne la position initiale de la salle.
     * @return la position initiale de la salle.
     */
    public Position getPosInit(){
        return this.posInit;
    }

    /**
     * Crée un objet Salle avec les paramètres fournis.
     * @param nom nom de l’élément
     */
    public Salle(String nom){
        this.nom = nom;
        this.objetParSol = new ArrayList<Item>();
        this.personnages = new ArrayList<Personnage>();
        this.portes = new HashMap<Direction,Porte>();
    }
    
    
    /**
     * Retourne le nom de la salle.
     * @return le nom de la salle.
     */
    public String getSalleNom(){
        return this.nom;
    }

    /**
     * Associe une porte à la salle dans la direction donnée.
     * @param dir direction de la porte
     * @param porte porte concernée
     */
    public void addPorte(Direction dir, Porte porte){
        this.portes.put(dir, porte);
    }

    /**
     * Retourne la porte située dans la direction donnée.
     * @param dir direction de la porte
     * @return la porte associée à la direction, ou {@code null} si elle n’existe pas.
     */
    public Porte getPorte(Direction dir){
        return this.portes.get(dir);
    }


    /**
     * Retourne la liste des objets posés au sol dans la salle.
     * @return la liste des objets posés au sol.
     */
    public ArrayList<Item> getObjetsParSol() {
        return this.objetParSol;
    }

    /**
     * Retire un objet du sol de la salle.
     * @param ob objet concerné
     */
    public void prendreObjet(Item ob){
        objetParSol.remove(ob);
    }

    /**
     * Dépose un objet dans la salle.
     * @param ob objet concerné
     */
    public void deposerObjet(Item ob){
        objetParSol.add(ob);
    }

    /**
     * Ajoute un personnage dans la salle.
     * @param perso personnage concerné
     */
    public void ajouterPersonnage(Personnage perso) {
        this.personnages.add(perso);
    }

    /**
     * Retire un personnage de la salle.
     * @param perso personnage concerné
     */
    public void supprimerPersonnage(Personnage perso){
        this.personnages.remove(perso);
    }

    /**
     * Retourne les portes de la salle, indexées par direction.
     * @return les portes de la salle, associées à leur direction.
     */
    public Map<Direction,Porte> getPortes(){
        return this.portes;
    }
    
    /**
     * Retourne les personnages présents dans la salle.
     * @return la liste des personnages présents dans la salle.
     */
    public ArrayList<Personnage> getPersonnages(){
        return this.personnages;
    }
}

