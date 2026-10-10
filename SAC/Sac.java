package SAC;

import java.util.*;

public class Sac {
    private int capacité;

    private int remplissage;

    ArrayList<Item> items = new ArrayList<Item>();



    public Sac(int capacité){
        this.capacité=capacité;
    }

    public int get_remplissage(){
        return  this.remplissage;
    }

    public void add_capacité(int plus){
        this.capacité+=plus;
    }


    public boolean  collect(Item item){
        if ((remplissage+item.size)>this.capacité){
            return false;
        }

        this.items.add(item);
        remplissage+=item.size;
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