package Personnages;

import SAC.*;
import MAPS.*;
public abstract class Personnage {
    private String nom;
    private int pvInit;
    private int pv;
    private Position pos;
    
    public int getPvInit() {
        return this.pvInit;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }
    public Position getPosition(){
        return this.pos;
    }
    public void setPosition(int x, int y){
        this.pos = new Position(x, y);
    }
    public int getPv(){
        return this.pv;
    }

    public void setPv(int pv) {
        // tu ne peux pas depasser le pv initialement fixe
        if (pv < pvInit) {
            this.pv = Math.max(0, pv);
        } else {
            this.pv = pvInit;
        }
    }

    public boolean getEstMort() {
        return (this.pv <= 0);
    }


    public Personnage(String nom, int pvInit) {
        this.nom = nom;
        this.pv = pvInit;
        this.pvInit = pvInit; 
    }

    public void attaquer(Personnage victime, Arme a) {
        if (!victime.getEstMort() && a!=null){
            victime.setPv(victime.getPv() - a.getDegat());
        }
    }
}






