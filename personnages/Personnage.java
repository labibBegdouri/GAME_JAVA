public abstract class Personnage {
    static int pvInit;
    private int pv;
    private boolean estMort;

    public int getPv(){
        return this.pv;
    }

    public void setPv(int pv) {
        // tu peux pas depasser le pv initialement fixe
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
            if (this.getPv() <= a.degat){
                this.setPv(0);
                this.setEstMort(true);
            } else {
                this.setPv(this.getPv() - a.degat);
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

    public void consommer(Food f) {
        // Si tu consommes qlq chose avec la barre de vie rempli, tu vas perdre ton consommable
        int newPv = this.getPv() + food.bonus;
        this.setPv(newPv);
    }
}

class Monstre extends Personnage{
    private Arme a;
    public Monstre(int pvInit, Arme a) {
        super(pvInit);
        this.arme = a;
    }
}

class Allie extends Personnage {
    private boolean trahison;
    private Arme a;

    public Allie(int pvInit, Arme a){
        super(pvInit);
        this.arme = a;
    }
}