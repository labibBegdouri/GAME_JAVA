package SAC;

import MAPS.*;


public class Tresor {
    private Item tresor ;



    private Position pos;


    public Position getPosition(){
        return this.pos;
    }
    public void setPosition(int x,int y){
        this.pos= new Position(x,y);
    }

    public Tresor(Item tresor){
            this.tresor=tresor;
    }

    public Item getTresor() {
        return this.tresor;
    }

    public void setTresor(Item tresor) {
        this.tresor=tresor;
    }

    public String getString(){
        return "{TRESOR !!!!!!!!!!!! L7WA!!!!!!!!!!!!!!!!}" ;
        
    }

    
}
