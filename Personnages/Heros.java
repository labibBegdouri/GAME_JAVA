package Personnages;

import java.util.ArrayList;
import java.util.List;

import MAPS.*;
import SAC.*;

public class Heros extends Personnage{
    private Sac sac;
    private ArrayList<Allie> allieHeros;
    
    public Heros(int pvInit,int capacite) {
        super("HERO",pvInit);
        this.sac = new Sac(capacite);
        this.allieHeros = new ArrayList<Allie>();
    }

    public ArrayList<Allie> getAllieHeros() {
        return this.allieHeros;
    }

    public void addAllie(Allie a, Salle salleCourante) {
        this.allieHeros.add(a);
        salleCourante.supprimerPersonnage(a);
    }

    public void removeAllie(Allie a) {
        this.allieHeros.remove(a);
    }

    public Sac getSac() {
        return this.sac;
    }

    public List<Arme> getArmesDuSac() {
        ArrayList<Arme> armes = new ArrayList<>();
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Arme) {
                armes.add((Arme) item);
            }
        }
        return armes;
    }

    public ArrayList<Food> getFoodDuSac() {
        ArrayList<Food> foods = new ArrayList<>();
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Food) {
                foods.add((Food) item);
            }
        }
        return foods;
    }

    public void consommer(Food f) {
        // Si tu consommes qlq chose avec la barre de vie rempli, tu vas perdre ton consommable
        int newPv = this.getPv() + f.getBonus();
        this.setPv(newPv);
        this.sac.remove(f);
    }

    public boolean possedeTresor() {
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Tresor) {
                return true;
            }
        }
        return false;
    }
}