package TP1.heuristique;

import TP1.But;
import TP1.Etat;
import TP1.Planete;

public class Diagonal extends Heuristique {

    public Diagonal(Planete planete) {
        super(planete);
    }

    @Override
    public double estimerCoutRestant(Etat etat, But but) {
        double htepienX, htepienY, bombeX,bombeY;
        htepienX = etat.getEmplacementHtepien().getPositionGeographique().getX();
        htepienY = etat.getEmplacementHtepien().getPositionGeographique().getY();
        bombeX = etat.getEmplacementBombe(0).getPositionGeographique().getX();
        bombeY = etat.getEmplacementBombe(0).getPositionGeographique().getY();

        double deltaX = bombeX - htepienX;
        double deltaY = bombeY - htepienY;

        return Math.sqrt((Math.pow(deltaX,2)+ Math.pow(deltaY,2)));
    }
}
