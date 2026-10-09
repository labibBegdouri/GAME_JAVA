error id: file://<WORKSPACE>/LecteurMaison.java:java/util/Map#get().
file://<WORKSPACE>/LecteurMaison.java
empty definition using pc, found symbol in pc: java/util/Map#get().
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 2508
uri: file://<WORKSPACE>/LecteurMaison.java
text:
```scala
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class LecteurMaison{
    public Maison chargerDepuisFichier(String chemin){ // relier les differentes classes 
        
        Maison maison = new Maison();

        return maison;
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

class Porte{

    private boolean fermee;  //  Porte O ou F
    private String cleId; //Si F
    private Salle salleA;
    private Salle salleB;

    public Porte(String cleId){ // pour F

        this.fermee = true;
        this.cleId = cleId;

    }

    public Porte(){ //pour O
        this.fermee = false;
        this.cleId = null;
    }

    public void setSalleA(Salle salle){

        this.salleA = salleA;

    }

    public void setSalleB(Salle salle){
        this.salleB = salleB;
        
    }

    

    public boolean estFermee(){ //verifier si P est F
        return this.fermee;
    }

    public boolean ouvrirPorte(String cleId){ //verifier si cleId est la cle de P
        if (cleId.isEmpty()){
            return true;
        }
        if (this.cleId.equals(cleId)){
            this.fermee = false;
            return true;
        }
        return false;
    }

}
enum Direction{
    HAUT,
    BAS,
    DROITE,
    GAUCHE
}
class Salle{
    private String nom;
    private List<Consommables> objetParSol;
    private Map<Direction, Porte> portes;

    public Salle(String nom){
        this.nom = nom;
        this.objetParSol = new ArrayList<Consommables>();
        this.portes = new HashMap<Direction,Porte>();
    }
    
    
    public String getSalleNom(){
        return this.nom;
    }

    public void addPorte(Direction dir, Porte porte){
        this.portes.put(dir, porte);
    }

    public Porte getPorte(Direction p){
        return this.portes.get@@(p);
    }

    public void prendreObjet(Consommables ob){
        objetParSol.remove(ob);
    }

    public void deposerObjet(Consommables ob){
        objetParSol.add(ob);
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


}


class Consommables{ // Labib 

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/Map#get().