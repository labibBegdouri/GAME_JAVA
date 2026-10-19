package SAC;

import MAPS.*;


public class Tresor extends Item {

    private Position pos;


    public Position getPosition(){
        return this.pos;
    }
    public void setPosition(int x,int y){
        this.pos= new Position(x,y);
    }

    public Tresor(String nom, int id){
        super(nom,id);
    }
    public String totString(){
        return "{TRESOR}" ;
        
    }

    
}
