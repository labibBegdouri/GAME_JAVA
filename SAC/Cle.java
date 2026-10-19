package SAC;

public class Cle extends Item {
    private String cleId;

    public Cle(String nom, int id, int size, String cleId) {
        super(nom, id, size);
        this.cleId = cleId;
    }

    public Cle(String nom, int id, String cleId) {
        super(nom, id); 
        this.cleId = cleId;
    }

    public String getCleId() {
        return this.cleId;
    }

    @Override
    public String toString(){
        return "{ name = "+ getNom()+", Type = Clé" + " }" ;
        
    }
}