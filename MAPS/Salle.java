package MAPS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import SAC.*;
import Personnages.*;
public class Salle{
    private String nom;
    private List<Item> objetParSol;
    private List<Personnage> personnages;
    private Map<Direction, Porte> portes;
    

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

    public List<Item> getObjetsParSol() {
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
    
}

