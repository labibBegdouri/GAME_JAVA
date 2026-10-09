error id: file://<WORKSPACE>/LecteurMaison.java:java/lang/String#
file://<WORKSPACE>/LecteurMaison.java
empty definition using pc, found symbol in pc: java/lang/String#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1633
uri: file://<WORKSPACE>/LecteurMaison.java
text:
```scala
import java.util.ArrayList;
import java.util.HashMap;


public class LecteurMaison{
    public Maison chargerDepuisFichier(String chemin){ // relier les differentes classes 
        
        Maison maison = new Maison();

        return maison;
    }
}



class Position {
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
        return x*y*z;
    }

}

class Porte{

    private boolean Fermee;  //  Porte O ou F
    private String cleId; //Si F

    public Porte(String cleId){ // pour F

        this.Fermee = true;
        this.cleId = cleId;

    }

    public Porte(){ //pour O
        this.Fermee = false;
        this.cleId = null;
    }

    public boolean estFermee(){ //verifier si P est F
        return this.Fermee;
    }

    public boolean ouvrirPorte(String cleId){ //verifier si cleId est la cle de P
        return this.cleId.equals(cleId);
    }

}

class Salle{
    private String nom;
    private ArrayList<Consommables> objetParSol; 
    private Map<Position, Porte> portes;

    public Salle(S@@tring nom){
        this.nom = nom;
        this.objetParSol = new ArrayList<Consommables>();
        this.portes = new HashMap<Position,Porte>();
    }
    
    public String getSalleNom(){
        return this.nom;
    }

    public void addPorte(Position p, Porte porte){
        this.portes.put(p, porte);
    }

    public Porte getPorte(Position p){
        return this.portes.get(p);
    }

    public void prendreObjet(Consommables ob){
        objetParSol.remove(ob);
    }

    public void deposerObjet(Consommables ob){
        objetParSol.add(ob);
    }
    
}

class Maison {
    private HashMap<String,Salle> salles;
    private String salleReveil;
    private String salleTresor;

    public Maison(){
        this.salles = new HashMap<String,Salle>();
    }

    public Maison(String salleReveilOrTresor, boolean reveilOrTresor){  //reveil: true tresor: false

        if (reveilOrTresor){
            this.salleReveil = salleReveilOrTresor;
        }else{
            this.salleTresor = salleReveilOrTresor;
        }
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

empty definition using pc, found symbol in pc: java/lang/String#