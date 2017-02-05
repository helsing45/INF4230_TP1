package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

import TP1.heuristique.Heuristique;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Représente un état dans le monde.
 */
public class Etat implements Comparable<Etat> {

    public static final String ACTION_NORD = "Nord",
            ACTION_SUD = "Sud",
            ACTION_OUEST = "Ouest",
            ACTION_EST = "Est",
            ACTION_CHARGER = "Charger",
            ACTION_DECHARGER = "Decharger";


    // Référence sur le planete (pour accéder aux objets du planete).
    protected Planete planete;

    public Planete getPlanete() {
        return planete;
    }

    // Noyau de la représentation d'un état. Ici, on met tout ce qui rend l'état unique.
    /**
     * Emplacement du Htepien.
     */
    protected Emplacement emplacementHtepien;
    /**
     * Array indicant l'emplacement de chaque bombes (il y'en a qu'une seule dans ce TP).
     */
    protected Emplacement emplacementsBombes[];
    /**
     * Array indicant l'état de chargement de chaque bombes pat le Htepien.
     */
    protected boolean bombesCharges[];
    /**
     * Nombre de bombes que porte le Htepien.
     */
    protected int nbbombesCharges = 0;


    // Variables pour l'algorithme A*.
    /**
     * État précédent permettant d'atteindre cet état.
     */
    protected Etat parent;
    /**
     * Action à partir de parent permettant d'atteindre cet état.
     */
    protected String actionFromParent;
    /**
     * f=g+h.
     */
    protected double f;
    /**
     * Meilleur coût trouvé pour atteindre cet été à partir de l'état initial.
     */
    protected double g;
    /**
     * Estimation du coût restant pour atteindre le but.
     */
    protected double h;


    public Etat(Planete planete) {
        this.planete = planete;
    }

    public Emplacement getEmplacementHtepien() {
        return emplacementHtepien;
    }

    public Emplacement getEmplacementBombe(int index) {
        return emplacementsBombes[index];
    }

    public Emplacement[] getEmplacementsBombes() {
        return emplacementsBombes;
    }

    public void clear() {
        f = g = h = 0;
        parent = null;
    }

    public LinkedList<Successeur> genererSuccesseurs(Heuristique heuristique, Emplacement but) {
        LinkedList<Successeur> successeurs = new LinkedList<Successeur>();

        // Verifier si on est sur la bombe
        if (getEmplacementBombe(0) == getEmplacementHtepien() && nbbombesCharges == 0) {
            successeurs.add(new Successeur(clone(), ACTION_CHARGER, 30));
            return successeurs;
        }

        // Verifier si on est sur la sortie
        if (getEmplacementHtepien() == planete.getSortie()) {
            successeurs.add(new Successeur(clone(), ACTION_DECHARGER, 30));
            return successeurs;
        }

        List<Route> routePossible = getPlanete().emplacements.get(getEmplacementHtepien().nom).routes;
        for (Route route : routePossible) {
            String movingAction = route.getAction();
            if (!movingAction.isEmpty()) {
                Etat etatSuccesseurs = clone();
                etatSuccesseurs.emplacementHtepien = route.destination;
                Successeur successeur = new Successeur(etatSuccesseurs, movingAction, route.obtenirCoutRoute(planete.vitesse));
                successeur.etat.g = (g + successeur.cout);
                successeur.etat.parent = this;
                successeur.etat.h = heuristique.estimerCoutRestant(successeur.etat, but);
                successeur.etat.calculerF();
                successeurs.add(successeur);
            }
        }
        Collections.sort(successeurs);
        return successeurs;
    }

    public LinkedList<Successeur> genererSuccesseurs() {
        LinkedList<Successeur> successeurs = new LinkedList<Successeur>();

        // Verifier si on est sur la bombe
        if (getEmplacementBombe(0) == getEmplacementHtepien() && nbbombesCharges == 0) {
            successeurs.add(new Successeur(clone(), ACTION_CHARGER, 30));
            return successeurs;
        }

        // Verifier si on est sur la sortie
        if (getEmplacementHtepien() == planete.getSortie()) {
            successeurs.add(new Successeur(clone(), ACTION_DECHARGER, 30));
            return successeurs;
        }

        List<Route> routePossible = getPlanete().emplacements.get(getEmplacementHtepien().nom).routes;
        for (Route route : routePossible) {
            String movingAction = route.getAction();
            if (!movingAction.isEmpty()) {
                Etat etatSuccesseurs = clone();
                etatSuccesseurs.emplacementHtepien = route.destination;
                successeurs.add(new Successeur(etatSuccesseurs, movingAction, route.obtenirCoutRoute(planete.vitesse)));
            }
        }

        return successeurs;
    }


    /**
     * Crée un nouvel État en copiant l'état présent.
     * Effectue une copie en surface.
     * En principe, vous n'aurez pas à modifier la méthode clone().
     */
    @Override
    public Etat clone() {
        Etat etat2 = new Etat(planete);
        etat2.nbbombesCharges = nbbombesCharges;
        etat2.emplacementHtepien = emplacementHtepien;
        etat2.emplacementsBombes = new Emplacement[emplacementsBombes.length];
        for (int i = 0; i < emplacementsBombes.length; i++)
            etat2.emplacementsBombes[i] = emplacementsBombes[i];

        etat2.bombesCharges = new boolean[bombesCharges.length];
        for (int i = 0; i < bombesCharges.length; i++)
            etat2.bombesCharges[i] = bombesCharges[i];
        return etat2;
    }

    /**
     * Relation d'ordre nécessaire pour TreeSet.
     */
    @Override
    public int compareTo(Etat o) {
        int c;
        c = emplacementHtepien.compareTo(o.emplacementHtepien);
        if (c != 0) return c;

        c = nbbombesCharges - o.nbbombesCharges;
        if (c != 0) return c;

        for (int i = 0; i < emplacementsBombes.length; i++) {
            c = (bombesCharges[i] ? 1 : 0) - (o.bombesCharges[i] ? 1 : 0);
            if (c != 0) return c;
            if (!bombesCharges[i]) {
                c = emplacementsBombes[i].compareTo(o.emplacementsBombes[i]);
                if (c != 0) return c;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String s = "ETAT: f=" + f + "  g=" + g + "\n";
        s += "  Pos=" + emplacementHtepien.nom + "";
        for (int i = 0; i < emplacementsBombes.length; i++) {
            s += "\n  PosBombes[i]=";
            s += emplacementsBombes[i] == null ? "--" : emplacementsBombes[i].nom;
        }
        s += "\n";
        return s;
    }

    public void calculerF() {
        f = g + h;
    }
}
