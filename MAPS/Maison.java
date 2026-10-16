package MAPS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import SAC.*;


public class Maison {
    private Map<String,Salle> salles;
    private String salleReveil;
    private Position posReveil;
    private String salleTresor;
    private Position posTresor;

    public Maison(){
        this.salles = new HashMap<String,Salle>();
    }

    public void setSalleReveil(String nom) { 
        this.salleReveil = nom; 
    }
    public void setSalleTresor(String nom) { 
        this.salleTresor = nom; 
    }

    public void ajouterSalle(Salle salle){
        this.salles.put(salle.getSalleNom(), salle);
    }

    public Salle getSalleReveil(){
        return this.salles.get(salleReveil);
    }

    public Salle getSalleTresor(){
        return this.salles.get(salleTresor);
    }

    public Salle getSalle(String nom){
        return this.salles.get(nom);
    }

    public Position getPosReveil(){
        return this.posReveil;
    }

    public void setPosReveil(int x, int y){
        this.posReveil = new Position(x, y);
    }

    public Position getPosTresor(){
        return this.posTresor;
    }

    public void setPosTresor(int x, int y){
        this.posTresor = new Position(x, y);
    }

}