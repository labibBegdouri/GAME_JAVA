import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class LecteurMaison{
    public Maison chargerDepuisFichier(String chemin){ // charger la map depuis le chemin
        
        Maison maison = new Maison();
        // extraire la data utile
        ArrayList<String> dataUtile = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))){
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")){
                    continue;
                }

                String[] ligneSansEspaces = ligne.split("\\s+");
                for (String mot : ligneSansEspaces){
                    dataUtile.add(mot.toUpperCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dataUtile.size() < 2 ){
            return maison;
        }

        int nbLignes = Integer.parseInt(dataUtile.get(0));
        int nb_colones = Integer.parseInt(dataUtile.get(1));
        dataUtile.subList(0, 3).clear();
        //map d un seule etage sous forme d une matrice
        String[][] map = new String[nbLignes][nb_colones];

        for (int y = 0; y < nbLignes; y++){
            for (int x = 0; x < nb_colones; x++){
                map[y][x] = dataUtile.get(y*nbLignes + x);
            }
        }

        // analyse de la map pour relier les classes




        
        
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
        return this.fermee;
    }

    public boolean ouvrirPorte(String cleId){ //verifier si cleId est la cle de P

        if (this.cleId != null && this.cleId.equals(cleId)){
            this.fermee = false;
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

    public Porte getPorte(Direction dir){
        return this.portes.get(dir);
    }

    public List<Consommables> getObjetsParSol() {
        return this.objetParSol;
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