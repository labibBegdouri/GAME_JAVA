package Personnages;

import java.util.ArrayList;
import java.util.List;

import SAC.*;

public class Heros extends Personnage{
    private Sac sac;
    
    public Heros(int capacite) {
        super("HERO");
        this.sac = new Sac(capacite);
    }

    public Sac getSac() {
        return this.sac;
    }

    public List<Arme> getArmesDuSac() {
        List<Arme> armes = new ArrayList<>();
        for (Item item : this.sac.getSAC()) {
            if (item instanceof Arme) {
                armes.add((Arme) item);
            }
        }
        return armes;
    }

    public List<Food> getFoodDuSac() {
        List<Food> foods = new ArrayList<>();
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
}