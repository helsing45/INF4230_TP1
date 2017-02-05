package TP1.heuristique;

import TP1.Emplacement;
import TP1.Etat;
import TP1.Planete;

public class Diagonal extends Heuristique {

    public Diagonal(Planete planete) {
        super(planete);
    }

    @Override
    public double estimerCoutRestant(Etat etat, Emplacement but) {
        double htepienX, htepienY, bombeX,bombeY;
        htepienX = etat.getEmplacementHtepien().getPositionGeographique().getX();
        htepienY = etat.getEmplacementHtepien().getPositionGeographique().getY();
        bombeX = but.getPositionGeographique().getX();
        bombeY = but.getPositionGeographique().getY();

        double deltaX = bombeX - htepienX;
        double deltaY = bombeY - htepienY;

        return Math.sqrt((Math.pow(deltaX,2)+ Math.pow(deltaY,2)));
    }
}
