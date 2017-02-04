package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

import TP1.heuristique.Heuristique;

import java.util.*;

public class AStar {
    private static TreeSet<Etat> open, closed;
    private static Etat current;

    public static List<String> genererPlan(Etat etatInitial, But but, Heuristique heuristique) {
        List<String> plan = new ArrayList<>();
        open = new TreeSet<Etat>();
        closed = new TreeSet<Etat>();

        open.add(etatInitial);

        while (!open.isEmpty()) {
            current = open.first();
            LinkedList<Successeur> successeurs = current.genererSuccesseurs(heuristique, but);
            open.remove(current);
            closed.add(current);

            if (but.butEstStatisfait(current)) break;
            open.add(successeurs.getFirst().etat);
        }

        for (Etat etat : open) {
            plan.add(etat.toString());
        }
        return plan;
    }

}
