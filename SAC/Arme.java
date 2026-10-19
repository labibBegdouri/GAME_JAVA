package SAC;

public class Arme extends Item {
    private int degat;

    public  Arme(String nom,int id,int size,int degat){
        super(nom,id,size);
        this.degat=degat;
    }

    public int getDegat(){
        return this.degat;
    }
    
    public void setDegat(int degat){
        this.degat=degat;
    }   

    public void addDegat(int bonus){
        this.degat=degat+bonus;
    } 
    

    @Override
    public String toString(){
        return "{ name = "+ getNom()+", Type = Arme" + ", attack = "+ degat +" }" ;
        
    }

    
}