package MAPS;
import java.util.ArrayList;
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
    // private Map<Direction, Porte> portes;
    private List<Porte> portes;
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
        this.portes = new ArrayList<Porte>();
    }
    
    
    /**
     * Retourne le nom de la salle.
     * @return le nom de la salle.
     */
    public String getSalleNom(){
        return this.nom;
    }

    /**
     * Ajoute une porte dans la salle.
     * @param porte porte concernée
     */
    public void addPorte(Porte porte) {
        this.portes.add(porte);
    }

    /**
     * Retourne les portes de la salle
     * @return les portes de la salle, ou {@code null} si elles n’existent pas.
     */
    public List<Porte> getPortes() {
        return this.portes;
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
     * Retourne les personnages présents dans la salle.
     * @return la liste des personnages présents dans la salle.
     */
    public ArrayList<Personnage> getPersonnages(){
        return this.personnages;
    }
}

