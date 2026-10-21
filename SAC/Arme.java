package SAC;
/**
 * Objet représentant une arme et sa valeur de dégâts.
 */

public class Arme extends Item {
    private int degat;

    /**
     * Crée un objet Arme avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param id identifiant de l’élément
     * @param size taille de l’élément
     * @param degat valeur des dégâts
     */
    public  Arme(String nom,int id,int size,int degat){
        super(nom,id,size);
        this.degat=degat;
    }

    /**
     * Retourne les dégâts infligés par l’arme.
     * @return les dégâts infligés par l’arme.
     */
    public int getDegat(){
        return this.degat;
    }
    
    /**
     * Définit les dégâts infligés par l’arme.
     * @param degat valeur des dégâts
     */
    public void setDegat(int degat){
        this.degat=degat;
    }   

    /**
     * Ajoute un bonus aux dégâts de l’arme.
     * @param bonus valeur du bonus
     */
    public void addDegat(int bonus){
        this.degat=degat+bonus;
    } 
    

    @Override
    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString(){
        return "{ name = "+ getNom()+", Type = Arme" + ", attack = "+ degat +" }" ;
        
    }

    
}