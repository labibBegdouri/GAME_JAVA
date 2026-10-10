package SAC;

import java.util.*;

public class Sac {
    private int capacity;

    private int fullness=0;

    ArrayList<Item> items = new ArrayList<Item>();



    public Sac(int capacity){
        this.capacity=capacity;
    }
    public Sac(int capacity,int fullness){
        this.capacity=capacity;
        this.fullness=fullness;
    }

    public int getFullness(){
        return  this.fullness;
    }
    public void addFullness(int extra){
        this.fullness+=extra;
    }
    public void setFullness(int fullness){
        this.fullness=fullness;
    }

    public int getCapacity(){
        return  this.capacity;
    }
    public void addCapacity(int extra){
        this.capacity+=extra;
    }
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }




    public boolean  collect(Item item){
        if ((getFullness()+item.getSize())>getCapacity()){
            return false;
        }

        this.items.add(item);
        addFullness(item.getSize());
        return true;

    }

    public boolean remove(int indice){
        // return false;
        try{
            this.items.remove(indice);
        }
        catch (Exception e){
            return false;
        }
        return true;

    }




}