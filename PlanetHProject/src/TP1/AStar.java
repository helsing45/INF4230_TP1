package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

import TP1.heuristique.Heuristique;

import java.util.*;

public class AStar {
    private static PriorityQueue<Etat> open, closed;
    private static Etat current;

    public static List<String> genererPlan(Etat etatInitial, But but, Heuristique heuristique) {
        List<String> plan = new ArrayList<>();
        plan.addAll(generatePath(etatInitial, etatInitial.getEmplacementBombe(0), heuristique));
        current.nbbombesCharges ++;
        plan.add("Charger(r0,B);");
        plan.addAll(generatePath(current,etatInitial.planete.getSortie(),heuristique));
        plan.add("Decharger(r0,B);");
        return plan;
    }

    private static List<String> generatePath(Etat etatInitial, Emplacement but, Heuristique heuristique) {
        List<String> plan = new ArrayList<>();
        open = new PriorityQueue<Etat>();
        closed = new PriorityQueue<Etat>();

        open.add(etatInitial);

        while (!open.isEmpty()) {
            current = open.poll();
            LinkedList<Successeur> successeurs = current.genererSuccesseurs(heuristique, but);
            closed.add(current);

            if (current.emplacementHtepien == but) break;
            open.add(successeurs.getFirst().etat);
        }

        while (closed.size() != 1) {
            plan.add(printMovementEntre(closed.poll(), closed.peek()));
        }
        return plan;
    }

    private static String printMovementEntre(Etat origine, Etat destination) {
        return String.format("%1$s = Lieu %2$s -> Lieu %3$s);",
                Route.getAction(origine.emplacementHtepien.getPositionGeographique(), destination.emplacementHtepien.getPositionGeographique()),
                origine.emplacementHtepien.getNom(),
                destination.emplacementHtepien.getNom());
    }

}
