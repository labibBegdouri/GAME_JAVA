package SAC;

import java.util.Objects;

import MAPS.*;

public class  Item{
    private String nom;
    private int id; // or string ????
    private int size;
    private Position pos;

    public Position getPosition(){
        return this.pos;
    }


    public void setPosition(int x,int y){
        this.pos= new Position(x,y);
    }

    public  Item(String nom,int id,int size){
        this.nom=nom;
        this.id=id;
        this.size=size;
    }

    public  Item(String nom,int id){
        this.nom=nom;
        this.id=id;
        this.size=1;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getSize(){
        return this.size;
    }
    public void setSize(int size ){
        this.size=size;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Item)) {
            return false;
        }
        Item other = (Item) obj;
        return this.id == other.id && Objects.equals(this.nom, other.nom);
    }

    public int hashCode() {
        return Objects.hash(this.id, this.nom);
    }

    public String toString() {
        return "{ Nom = " + this.nom + ", Type = Objet, Taille = " + this.size + " }";
    }




}