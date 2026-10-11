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
    
    void setDegat(int degat){
        this.degat=degat;
    }   

    void addDegat(int bonus){
        this.degat=degat+bonus;
    } 
    

    int getDefense(){
        return this.defense;
    }
    
    void setDefense(int defense){
        this.defense=defense;
    }   

    void addDefense(int bonus){
        this.defense=this.defense+bonus;
    } 

    
}