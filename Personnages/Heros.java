package Personnages;

import java.util.ArrayList;
import java.util.List;

import MAPS.*;
import SAC.*;
/**
 * Héros du jeu, avec son sac et les alliés qui l’accompagnent.
 */

public class Heros extends Personnage{
    private Sac sac;
    private ArrayList<Allie> allieHeros;
    
    /**
     * Crée un objet Heros avec les paramètres fournis.
     * @param pvInit points de vie initiaux
     * @param capacite capacité du sac
     */
    public Heros(int pvInit,int capacite) {
        super("HERO",pvInit);
        this.sac = new Sac(capacite);
        this.allieHeros = new ArrayList<Allie>();
    }

    /**
     * Retourne la liste des alliés du héros.
     * @return la liste des alliés du héros.
     */
    public ArrayList<Allie> getAllieHeros() {
        return this.allieHeros;
    }

    /**
     * Ajoute un allié au groupe et le retire de la salle.
     * @param a arme utilisée
     * @param salleCourante salle actuellement occupée
     */
    public void addAllie(Allie a, Salle salleCourante) {
        this.allieHeros.add(a);
        salleCourante.supprimerPersonnage(a);
    }

    /**
     * Retire un allié du groupe du héros.
     * @param a arme utilisée
     */
    public void removeAllie(Allie a) {
        this.allieHeros.remove(a);
    }

    /**
     * Retourne le sac du héros.
     * @return le sac du héros.
     */
    public Sac getSac() {
        return this.sac;
    }

    /**
     * Retourne les armes contenues dans le sac du héros.
     * @return les armes présentes dans le sac du héros.
     */
    public List<Arme> getArmesDuSac() {
        ArrayList<Arme> armes = new ArrayList<>();
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Arme) {
                armes.add((Arme) item);
            }
        }
        return armes;
    }

    /**
     * Retourne les aliments contenus dans le sac du héros.
     * @return les aliments présents dans le sac du héros.
     */
    public ArrayList<Food> getFoodDuSac() {
        ArrayList<Food> foods = new ArrayList<>();
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Food) {
                foods.add((Food) item);
            }
        }
        return foods;
    }

    /**
     * Consomme un aliment et applique son bonus de points de vie.
     * @param f aliment consommé
     */
    public void consommer(Food f) {
        // Si tu consommes qlq chose avec la barre de vie rempli, tu vas perdre ton consommable
        int newPv = this.getPv() + f.getBonus();
        this.setPv(newPv);
        this.sac.remove(f);
    }

    /**
     * Indique si le sac du héros contient un trésor.
     * @return {@code true} si le sac contient un trésor, sinon {@code false}.
     */
    public boolean possedeTresor() {
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Tresor) {
                return true;
            }
        }
        return false;
    }
}