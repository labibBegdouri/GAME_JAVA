package SAC;
/**
 * Objet consommable qui apporte un bonus de points de vie.
 */

public class Food extends Item  {
    private int bonus;

    /**
     * Crée un objet Food avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param size taille de l’élément
     * @param bonus valeur du bonus
     */
    public Food(String nom, int size, int bonus) {
        super(nom, size);
        this.bonus = bonus;
    }

    /**
     * Crée un objet Food avec les paramètres fournis.
     * @param nom nom de l’élément
     * @param bonus valeur du bonus
     */
    public Food(String nom,int bonus){
            super(nom);
            this.bonus=bonus;

    }

    /**
     * Retourne le bonus de points de vie de l’aliment.
     * @return le bonus de points de vie de l’aliment.
     */
    public int getBonus(){
        return this.bonus;
    }

    /**
     * Définit le bonus de points de vie de l’aliment.
     * @param bonus valeur du bonus
     */
    public void setBonus(int bonus){
        this.bonus = bonus;
    }

    @Override
    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString(){
        return "{ name = "+ getNom()+", Type = Food" + ", Bonus = "+getBonus()+ ", Size = " + super.getSize() +  " }" ;
        
    }

    
}
