package Personnages;

import SAC.*;
import MAPS.*;
public abstract class Personnage {
    private String nom;
    private static int pvInit = 0;
    private int pv;
    private boolean estMort;
    private Position pos;

    public static void initialiserPvInit(int pvI){
        pvInit = pvI;
    }

    public int getPvInit(){
        return Personnage.pvInit;
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

    public Personnage(String nom) {
        this.nom = nom;
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






