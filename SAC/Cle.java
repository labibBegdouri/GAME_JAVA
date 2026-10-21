package SAC;
/**
 * Objet représentant une clé associée à l’identifiant d’une serrure.
 */

public class Cle extends Item {
    private String cleId;

    /**
     * Crée un objet Cle avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param id identifiant de l’élément
     * @param size taille de l’élément
     * @param cleId identifiant de clé ou de serrure
     */
    public Cle(String nom, int id, int size, String cleId) {
        super(nom, id, size);
        this.cleId = cleId;
    }

    /**
     * Crée un objet Cle avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param id identifiant de l’élément
     * @param cleId identifiant de clé ou de serrure
     */
    public Cle(String nom, int id, String cleId) {
        super(nom, id); 
        this.cleId = cleId;
    }

    /**
     * Retourne l’identifiant de la serrure correspondant à la clé.
     * @return l’identifiant de serrure associé à la clé.
     */
    public String getCleId() {
        return this.cleId;
    }

    @Override
    
    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString() {
        return "{ Nom = " + getNom() + ", Type = Cle, Porte = " + this.cleId + " }";
    }
}