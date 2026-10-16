

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import SAC.*;


public class LecteurMaison{
    public Maison chargerDepuisFichier(String chemin){ // charger la map depuis le chemin
        
        Maison maison = new Maison();
    
        List<String[]> memoirePortes = new ArrayList<String[]>(); //memoriser toutes les portes.

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))){
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")){
                    continue;
                }
                String salleName = "";
                String[] ligneSplit = ligne.split(":");
                String objetType = ligneSplit[0];
                Salle salleCourante = null;
                if (objetType.equals("Salle")){
                    salleName = ligneSplit[1];
                    salleCourante = new Salle(salleName);
                    maison.ajouterSalle(salleCourante);
                }else if (objetType.equals("PORTE")) {
                    memoirePortes.add(new String[]{salleName,ligne});
                }else{
                    traiterContenuCase(ligne, salleCourante, maison);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Connecter les portes.

        for (String[] infoPorte : memoirePortes){
            String salleName = infoPorte[0];
            String lignePorte = infoPorte[1];
            String[] lignePorteSplit = lignePorte.split(":");

            String etat = lignePorteSplit[1];
            String serrureId = lignePorteSplit[2];  //L'ID ou "aucune"
            Direction dir = Direction.valueOf(lignePorteSplit[3]);
            String nomSalleB = lignePorteSplit[4];

            Salle salleA = maison.getSalle(salleName);
            Salle salleB = maison.getSalle(nomSalleB);

            if (salleA != null && salleB != null && salleA.getPorte(dir) == null) {
                    
                    Porte porte = new Porte(etat, serrureId);
                    
                    porte.setSalleA(salleA);
                    porte.setSalleB(salleB);
                    
                    salleA.addPorte(dir, porte);
                    salleB.addPorte(directionOpposee(dir), porte);
                }
        }
        
        return maison;
    }

    private Direction directionOpposee(Direction dir) {
        switch (dir) {
            case HAUT: return Direction.BAS;
            case BAS: return Direction.HAUT;
            case GAUCHE: return Direction.DROITE;
            case DROITE: return Direction.GAUCHE;
            default: return null;
        }
    }

    private void traiterContenuCase(String contenu, Salle salle, Maison maison){
        String[] contenuSplit = contenu.split(":");
        String type = contenuSplit[0];

        switch (type) {
            case "REVEIL":
                maison.setSalleReveil(salle.getSalleNom());
                break;
            case "TRESOR":
                maison.setSalleTresor(salle.getSalleNom());
                break;
            case "ARME":
                if (contenuSplit.length >= 5) {
                     Arme a = new Arme(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), Integer.parseInt(contenuSplit[4]));
                     salle.deposerObjet(a);
                }
                break;
            case "FOOD":
                // Syntaxe attendue -> FOOD:Nom:Id:Size:Bonus (ex: FOOD:POMME:1:1:15)
                if (contenuSplit.length >= 5) {
                    Food f = new Food(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), Integer.parseInt(contenuSplit[4]));
                    salle.deposerObjet(f);
                }
                break;
            case "MONSTRE":
                if (contenuSplit.length >= 4) {
                        int pvInit = Integer.parseInt(contenuSplit[1]);
                        String nomArme = contenuSplit[2];
                        int degatArme = Integer.parseInt(contenuSplit[3]);
                    
                        Arme armeMonstre = new Arme(nomArme, 0, 1, degatArme);
                        Creature m = new Creature (pvInit, armeMonstre);
                        salle.ajouterPersonnage(m);
                    }
                    break;
            case "ALLIE":
                if (contenuSplit.length >= 5) {
                    int pvInit = Integer.parseInt(contenuSplit[1]);
                    String nomArme = contenuSplit[3];
                    int degatArme = Integer.parseInt(contenuSplit[4]);
                    
                    Arme armeAllie = new Arme(nomArme, 0, 1, degatArme);
                    boolean trahison = Boolean.parseBoolean(contenuSplit[2]);
                    Allie allie = new Allie(pvInit, trahison, armeAllie);
                    salle.ajouterPersonnage(allie);
                }
                break;
            case "CLE":
                if (contenuSplit.length >= 5) {
                    Cle c = new Cle(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), contenuSplit[4]);
                    salle.deposerObjet(c);
                }
                break;
            default:
                break;
        }
    }

}


class Porte{

    private String fermee;  //  Porte O ou F
    private String cleId; //Si F
    private Salle salleA;
    private Salle salleB;

    public Porte(String fermee, String cleId){ // pour F

        this.fermee = fermee;
        this.cleId = cleId;

    }


    public void setSalleA(Salle salle){

        this.salleA = salle;

    }

    public void setSalleB(Salle salle){
        this.salleB = salle;
        
    }

    public Salle getAutreSalle(Salle salleCourante) {
        if (salleCourante.equals(this.salleA)) {
            return this.salleB;
        } else if (salleCourante.equals(this.salleB)) {
            return this.salleA;
        }
        return null;
    }

    

    public boolean estFermee(){ //verifier si P est F
        return this.fermee.equals("F");
    }

    public boolean ouvrirPorte(String cleId){ //verifier si cleId est la cle de P

        if (this.cleId != null && this.cleId.equals(cleId)){
            this.fermee = "O";
            return true;
        }
        return false;
    }

}
enum Direction{ //Cas seule etage
    HAUT,
    BAS,
    DROITE,
    GAUCHE
}
class Salle{
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

class Maison {
    private Map<String,Salle> salles;
    private String salleReveil;
    private String salleTresor;

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


}

/*class Position {
    private int x;
    private int y;
    private int z; //Etage

    public Position(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    public boolean equals(Object other){
        if (other instanceof Position){
            Position o = (Position) other;
            return this.x == o.getX() && 
                   this.y == o.getY() &&
                    this.z == o.getZ();
        }
        return false;
    }

    public int hashCode(){  //A rediscuter hh
        return x+y+z;
    }

}*/