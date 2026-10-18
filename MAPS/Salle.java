package MAPS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import SAC.*;
import Personnages.*;

public class Salle{
    private String nom;
    private ArrayList<Item> objetParSol;
    private ArrayList<Personnage> personnages;
    private Map<Direction, Porte> portes;
    private Position posInit;
    private int hauteur;
    private int largeur;
    
    public  int getH(){
        return hauteur;
    }

    public  void setH(int h){
        this.hauteur = h;
    }

    public  int getL(){
        return largeur;
    }

    public  void setL(int l){
        this.largeur = l;
    }

    public void setPosInit(int x, int y){
        this.posInit = new Position(x, y);
    }
    public Position getPosInit(){
        return this.posInit;
    }

    public Salle(String nom){
        this.nom = nom;
        this.objetParSol = new ArrayList<Item>();
        this.personnages = new ArrayList<Personnage>();
        this.portes = new HashMap<Direction,Porte>();
    }
    
    
    public String getSalleNom(){
        return this.nom;
    }

    public void addPorte(Direction dir, Porte porte){
        this.portes.put(dir, porte);
    }

    public Porte getPorte(Direction dir){
        return this.portes.get(dir);
    }


    public ArrayList<Item> getObjetsParSol() {
        return this.objetParSol;
    }

    public void prendreObjet(Item ob){
        objetParSol.remove(ob);
    }

    public void deposerObjet(Item ob){
        objetParSol.add(ob);
    }

    public void ajouterPersonnage(Personnage perso) {
        this.personnages.add(perso);
    }

    public void supprimerPersonnage(Personnage perso){
        this.personnages.remove(perso);
    }

    public Map<Direction,Porte> getPortes(){
        return this.portes;
    }
    
    public ArrayList<Personnage> getPersonnages(){
        return this.personnages;
    }
}

