package SAC;


abstract class  Item{
    String nom;
    int id;
    int size;

    public  Item(String nom,int id,int size){
        this.nom=nom;
        this.id=id;
        this.size=size;
    }

    public  Item(String nom,int id){
        this.nom=nom;
        this.id=id;
        this.size=1;
    }



    public boolean isEqual(Item item){
        if (this.id==item.id ){
            return true;
        }
        return false;
    }


}