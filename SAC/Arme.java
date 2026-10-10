package SAC;

abstract public class Arme extends Item {
    private int degat;
    abstract void attaquer();
    abstract void defendre();
    public  Arme(String nom,int id,int size,int degat){
        super(nom,id,size);
        this.degat=degat;
    }

    int getDegat(){
        return this.degat;
    }
    
    void setDegat(int degat){
        this.degat=degat;
    }   

    void addDegat(int bonus){
        this.degat=degat+bonus;
    } 
    

    
}