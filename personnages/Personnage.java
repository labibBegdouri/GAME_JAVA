import SAC.*;

public abstract class Personnage {
    static int pvInit;
    private int pv;
    private boolean estMort;

    public int getPv(){
        return this.pv;
    }

    public void setPv(int pv) {
        // tu ne peux pas depasser le pv initialement fixe
        if (pv < pvInit) {
            this.pv = pv;
        } else {
            this.pv = pvInit;
        }
    }

    public boolean getEstMort() {
        return (this.pv <= 0);
    }

    public void setEstMort(boolean etat) {
        this.estMort = etat;
    }

    public Personnage(int pvInit) {
        this.pvInit = pvInit;
        this.pv = pvInit;
        this.estMort = false;
    }

    public void attaquer(Personnage victime, Arme a) {
        if (!victime.getEstMort()){
            if (victime.getPv() <= a.getDegat()){
                victime.setPv(0);
                victime.setEstMort(true);
            } else {
                victime.setPv(victime.getPv() - a.getDegat());
            }
        }
    }
}

class Heros extends Personnage{
    private Sac sac;
    
    public Heros(int pvInit, int capacite) {
        super(pvInit);
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

class Creature extends Personnage{
    private Arme arme;

    public Creature(int pvInit, Arme a) {
        super(pvInit);
        this.arme = a;
    }

    public void attaquer(Personnage victime) {
        if (!victime.getEstMort()){
            Arme a = this.arme;
            if (victime.getPv() <= a.getDegat()){
                victime.setPv(0);
                victime.setEstMort(true);
            } else {
                victime.setPv(victime.getPv() - a.getDegat());
            }
        }
    }
}

class Allie extends Creature {
    private boolean trahison;

    public Allie(int pvInit, boolean trahison, Arme a){
        super(pvInit, a);
        this.trahison = trahison;
    }
}
