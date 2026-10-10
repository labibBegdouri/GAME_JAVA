package SAC;

public class Food extends Item  {
    private int bonus;

    public Food(String nom, int id, int size, int bonus){
            super(nom,id,size);
            this.bonus=bonus;

    }

    public Food(String nom, int id, int bonus){
            super(nom,id);
            this.bonus=bonus;

    }

    public int getBonus(){
        return this.bonus;
    }

    public int setBonus(){
        return this.bonus;
    }


    
}
