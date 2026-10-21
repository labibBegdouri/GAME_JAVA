package SAC;
/**
 * Objet spécial nécessaire pour ouvrir la porte scellée de la carte.
 */



public class Tresor extends Item {

    /**
     * Crée un objet Tresor avec les paramètres fournis.
     * @param size nom de l’élément
     */
    public Tresor(int size) {
        super("Tresor", size);
    }
    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString(){
        return "{TRESOR}" ;
        
    }

    
}
