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



    public boolean isEqual(Item item){
        if (this.id==item.id ){
            return true;
        }
        return false;
    }


}