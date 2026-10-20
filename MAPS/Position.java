package MAPS;

import java.util.Objects;

public class Position {
    private int x;
    private int y;
    //private int z; //Etage

    public Position(int x, int y){
        this.x = x;
        this.y = y;
        //this.z = z;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    //public int getZ(){
      //  return this.z;
    //}

    public boolean equals(Object other){
        if (other instanceof Position){
            Position o = (Position) other;
            return this.x == o.getX() && 
                   this.y == o.getY() ;
                    //this.z == o.getZ();
        }
        return false;
    }

    public int hashCode(){  //A rediscuter hh
        return Objects.hash(this.x, this.y);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}