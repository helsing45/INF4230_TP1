package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

public class Heuristique {


    protected Planete planete;

    public Heuristique(Planete planete){
        this.planete = planete;
    }

    /** Estime et retourne le coût restant pour atteindre le but à partir d'un état.
     *  Attention : pour être admissible, cette fonction heuristique ne doit pas
     *  surestimer le coût restant.
     */
    public double estimerCoutRestant(Etat etat, But but){
        double htepienX, htepienY, bombeX,bombeY;
        htepienX = etat.emplacementHtepien.positionGeographique.getX();
        htepienY = etat.emplacementHtepien.positionGeographique.getY();
        bombeX = etat.emplacementsBombes[0].positionGeographique.getX();
        bombeY = etat.emplacementsBombes[0].positionGeographique.getY();

        double deltaX = bombeX - htepienX;
        double deltaY = bombeY - htepienY;

        return Math.sqrt((Math.pow(deltaX,2)+ Math.pow(deltaY,2)));
    }

}
