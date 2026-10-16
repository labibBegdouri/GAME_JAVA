package Personnages;

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

    public void consommer(Food f) {
        // Si tu consommes qlq chose avec la barre de vie rempli, tu vas perdre ton consommable
        int newPv = this.getPv() + f.getBonus();
        this.setPv(newPv);
        this.sac.remove(f);
    }
}