package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

/**
 * Contient les éléments pertinent à propos d'un successeur (une transition
 * dans le graphe) : l'état suivant, l'action et son coût.
 */
public class Successeur implements Comparable<Successeur> {

    protected Etat etat;
    protected String action;
    protected double cout;

    public Successeur(Etat etat, String action, double cout) {
        this.etat = etat;
        this.action = action;
        this.cout = cout;
    }

    @Override
    public int compareTo(Successeur o) {
        if(etat == null) return -1;
        if(o.etat == null) return -1;
        return Double.compare(etat.f, o.etat.f);
    }
}
