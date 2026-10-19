package SAC;

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



    public boolean isEqual(Item item){
        if (this.id==item.id ){
            return true;
        }
        return false;
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


    public String toString(){
        return "";
        
    }




}