package MAPS;
import java.util.HashMap;
import java.util.Map;
/**
 * Contient les salles de la maison ainsi que les emplacements du réveil et du trésor.
 */


public class Maison {
    private Map<String,Salle> salles;
    private String salleReveil;
    private Position posReveil;
    private String salleTresor;
    private Position posTresor;

    /**
     * Crée un objet Maison avec les paramètres fournis.
     */
    public Maison(){
        this.salles = new HashMap<String,Salle>();
    }

    /**
     * Effectue l’opération setSalleReveil.
     * @param nom nom de l’élément
     */
    public void setSalleReveil(String nom) { 
        this.salleReveil = nom; 
    }
    /**
     * Effectue l’opération setSalleTresor.
     * @param nom nom de l’élément
     */
    public void setSalleTresor(String nom) { 
        this.salleTresor = nom; 
    }

    /**
     * Ajoute une salle à la maison.
     * @param salle salle concernée
     */
    public void ajouterSalle(Salle salle){
        this.salles.put(salle.getSalleNom(), salle);
    }

    /**
     * Retourne la salle de réveil.
     * @return la salle de réveil, ou {@code null} si elle n’est pas définie.
     */
    public Salle getSalleReveil(){
        return this.salles.get(salleReveil);
    }

    /**
     * Retourne la salle du trésor.
     * @return la salle du trésor, ou {@code null} si elle n’est pas définie.
     */
    public Salle getSalleTresor(){
        return this.salles.get(salleTresor);
    }

    /**
     * Recherche une salle par son nom.
     * @param nom nom de l’élément
     * @return la salle portant le nom fourni, ou {@code null} si elle est absente.
     */
    public Salle getSalle(String nom){
        return this.salles.get(nom);
    }

    /**
     * Retourne la position du réveil.
     * @return la position du réveil.
     */
    public Position getPosReveil(){
        return this.posReveil;
    }

    /**
     * Définit la position du réveil.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public void setPosReveil(int x, int y){
        this.posReveil = new Position(x, y);
    }

    /**
     * Retourne la position du trésor.
     * @return la position du trésor.
     */
    public Position getPosTresor(){
        return this.posTresor;
    }

    /**
     * Définit la position du trésor.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public void setPosTresor(int x, int y){
        this.posTresor = new Position(x, y);
    }

}