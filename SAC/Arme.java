package SAC;

public class Arme extends Item {
    private int degat;
    private int defense;
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
    

    public int getDefense(){
        return this.defense;
    }
    
    public void setDefense(int defense){
        this.defense=defense;
    }   

    public void addDefense(int bonus){
        this.defense=this.defense+bonus;
    } 

    
}