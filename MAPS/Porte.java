package MAPS;


class Porte{

    private String fermee;  //  Porte O ou F
    private String cleId; //Si F
    private Salle salleA;
    private Salle salleB;
    private Position pos;

    public Position getPosition(){
        return this.pos;
    }
    public void setPosition(int x, int y){
        this.pos = new Position(x, y);
    }
    public Porte(String fermee, String cleId){ // pour F

        this.fermee = fermee;
        this.cleId = cleId;

    }


    public void setSalleA(Salle salle){

        this.salleA = salle;

    }

    public void setSalleB(Salle salle){
        this.salleB = salle;
        
    }

    public Salle getAutreSalle(Salle salleCourante) {
        if (salleCourante.equals(this.salleA)) {
            return this.salleB;
        } else if (salleCourante.equals(this.salleB)) {
            return this.salleA;
        }
        return null;
    }

    

    public boolean estFermee(){ //verifier si P est F
        return this.fermee.equals("F");
    }

    public boolean ouvrirPorte(String cleId){ //verifier si cleId est la cle de P

        if (this.cleId != null && this.cleId.equals(cleId)){
            this.fermee = "O";
            return true;
        }
        return false;
    }

}
enum Direction{ //Cas seule etage
    HAUT,
    BAS,
    DROITE,
    GAUCHE
}