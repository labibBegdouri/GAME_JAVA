package MAPS;
/**
 * Porte reliant deux salles, éventuellement verrouillée par une clé ou le trésor.
 */


public class Porte{

    private String fermee;  //  Porte O ou F
    private String cleId; //Si F ou "TRESOR"
    private Salle salleA;
    private Salle salleB;
    private Position pos;

    /**
     * Retourne la position de l’objet ou de la porte.
     * @return la position de la porte.
     */
    public Position getPosition(){
        return this.pos;
    }
    /**
     * Définit la position à partir des coordonnées fournies.
     * @param x coordonnée horizontale
     * @param y coordonnée verticale
     */
    public void setPosition(int x, int y){
        this.pos = new Position(x, y);
    }
    /**
     * Crée un objet Porte avec les paramètres fournis.
     * @param fermee valeur utilisée par la méthode
     * @param cleId identifiant de clé ou de serrure
     */
    public Porte(String fermee, String cleId){ // pour F

        this.fermee = fermee;
        this.cleId = cleId;

    }


    /**
     * Effectue l’opération setSalleA.
     * @param salle salle concernée
     */
    public void setSalleA(Salle salle){

        this.salleA = salle;

    }

    /**
     * Effectue l’opération setSalleB.
     * @param salle salle concernée
     */
    public void setSalleB(Salle salle){
        this.salleB = salle;
        
    }

    /**
     * Retourne la salle reliée à la porte, autre que la salle courante.
     * @param salleCourante salle actuellement occupée
     * @return l’autre salle reliée à la porte, ou {@code null} si la salle donnée n’est pas reliée.
     */
    public Salle getAutreSalle(Salle salleCourante) {
        if (salleCourante.equals(this.salleA)) {
            return this.salleB;
        } else if (salleCourante.equals(this.salleB)) {
            return this.salleA;
        }
        return null;
    }

    

    /**
     * Indique si la porte est fermée.
     * @return {@code true} si la porte est fermée, sinon {@code false}.
     */
    public boolean estFermee(){ //verifier si P est F
        return this.fermee.equals("F");
    }

    /**
     * Indique si la porte nécessite le trésor pour être ouverte.
     * @return {@code true} si la porte exige le trésor, sinon {@code false}.
     */
    public boolean necessiteTresor() {
        return "TRESOR".equalsIgnoreCase(this.cleId);
    }

    /**
     * Ouvre la porte si elle nécessite le trésor.
     * @return {@code true} si le trésor ouvre la porte, sinon {@code false}.
     */
    public boolean ouvrirAvecTresor() {
        if (necessiteTresor()) {
            this.fermee = "O";
            return true;
        }
        return false;
    }

    /**
     * Ouvre la porte lorsque l’identifiant de clé correspond à sa serrure.
     * @param cleId identifiant de clé ou de serrure
     * @return {@code true} si la clé correspond et ouvre la porte, sinon {@code false}.
     */
    public boolean ouvrirPorte(String cleId){ //verifier si cleId est la cle de P

        if (this.cleId != null && this.cleId.equals(cleId)){
            this.fermee = "O";
            return true;
        }
        return false;
    }

}