package SAC;

abstract public class Food extends Item  {
    int bonus;

    public Food(String nom, int id, int size, int nb, int bonus){
            super(nom,id,size);
            this.bonus=bonus;

    }

    public Food(String nom, int id, int nb, int bonus){
            super(nom,id);
            this.bonus=bonus;

    }


    
}
