package SAC;

import java.util.*;
/**
 * Inventaire qui stocke des objets et limite leur taille totale selon sa capacité.
 */

public class Sac {
    private int capacity;

    private int fullness=0;

    private ArrayList<Item> items = new ArrayList<Item>();



    /**
     * Crée un objet Sac avec les paramètres fournis.
     * @param capacity capacité maximale du sac
     */
    public Sac(int capacity){
        this.capacity=capacity;
    }
    /**
     * Crée un objet Sac avec les paramètres fournis.
     * @param capacity capacité maximale du sac
     * @param fullness taille actuellement occupée
     */
    public Sac(int capacity,int fullness){
        this.capacity=capacity;
        this.fullness=fullness;
    }

    /**
     * Retourne la taille totale des objets actuellement dans le sac.
     * @return la taille totale occupée par les objets du sac.
     */
    public int getFullness(){
        return  this.fullness;
    }
    /**
     * Ajoute la quantité indiquée à la taille occupée.
     * @param extra quantité à ajouter
     */
    public void addFullness(int extra){
        this.fullness+=extra;
    }
    /**
     * Définit la taille actuellement occupée dans le sac.
     * @param fullness taille actuellement occupée
     */
    public void setFullness(int fullness){
        this.fullness=fullness;
    }

    /**
     * Retourne la capacité maximale du sac.
     * @return la capacité maximale du sac.
     */
    public int getCapacity(){
        return  this.capacity;
    }
    /**
     * Augmente la capacité maximale du sac.
     * @param extra quantité à ajouter
     */
    public void addCapacity(int extra){
        this.capacity+=extra;
    }
    /**
     * Définit la capacité maximale du sac.
     * @param capacity capacité maximale du sac
     */
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }


    /**
     * Retourne la liste des objets du sac.
     * @return la liste des objets contenus dans le sac.
     */
    public ArrayList<Item> getSAC(){
        return this.items;
    }

    /**
     * Ajoute l’objet au sac si sa capacité le permet.
     * @param item objet concerné
     * @return {@code true} si l’objet a été ajouté, sinon {@code false} si le sac est trop petit.
     */
    public boolean  collect(Item item){
        if ((getFullness()+item.getSize())>getCapacity()){
            return false;
        }

        this.items.add(item);
        addFullness(item.getSize());
        return true;

    }

    /**
     * Retire l’objet du sac et libère la place correspondante.
     * @param item objet concerné
     * @return {@code true} si l’objet a été retiré, sinon {@code false} s’il n’était pas dans le sac.
     */
    public boolean remove(Item item){
        // return false;
        if (this.items.remove(item)){
            this.addFullness(-item.getSize());
            return true;
        }
        return false;

    }
    
    @Override 
    /**
     * Retourne une représentation textuelle de cet objet.
     * @return une représentation textuelle de l’objet.
     */
    public String toString(){
        String output="Sac ={ ";
        int i=1;
        for (Item e: this.items){
            output=output+i+".<"+e.toString()+"> ";
            i++;

        }
        return output+"}";
    }




}