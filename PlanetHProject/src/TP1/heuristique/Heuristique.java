package TP1.heuristique;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */



import TP1.Etat;
import TP1.But;
import TP1.Planete;
public abstract class Heuristique {
    private Planete planete;

    public Heuristique(Planete planete) {
        this.planete = planete;
    }

    protected Planete getPlanete(){
        return planete;
    }

    /**
     * Estime et retourne le coût restant pour atteindre le but à partir d'un état.
     * Attention : pour être admissible, cette fonction heuristique ne doit pas
     * surestimer le coût restant.
     */
    public abstract double estimerCoutRestant(Etat etat, But but);
}
