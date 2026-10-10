package SAC;

abstract public class Arme extends Item {
    int degat;
    abstract void attaquer();
    abstract void defendre();
    public  Arme(String nom,int id,int size,int degat){
        super(nom,id,size);
        this.degat=degat;
    }
    
}
