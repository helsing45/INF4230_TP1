package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

import java.util.*;

public class AStar {
    private static TreeSet<Etat> open, closed;
    private static Etat current;

    public static List<String> genererPlan(Etat etatInitial, But but, Heuristique heuristique) {
        // Implémentez l'algorithme A* ici.
        //
        // Étapes suggérées :
        //  - Restez simple. Commencez avec TreeSet<Etat> open, closed;.
        closed = new TreeSet<>();
        open = new TreeSet<>();
        //  - Ajoutez etatInitial dans open.
        open.add(etatInitial);
        //  - Tracez les itérations dans la console avec System.out.println(..).
        //  - Pour chaque itération :
        int iteration = 0;
        while (!open.isEmpty()) {
            //Affichez le numéro d'itération.
            System.out.println("Iteration " + iteration);
            current = open.first();
            System.out.println(current.toString());
            //Vérifiez si l'état e satisfait le but.
            if (but.butEstStatisfait(current)) {
                //Si oui, sortez du while.
                break;
            }
            //Générez les successeurs de current
            List<Successeur> successeurs = new ArrayList<>(current.genererSuccesseurs());
            //Pour chaque état successeur s de e:
            for (Successeur successeur : successeurs) {
                //Vérifiez si s.etat est dans closed.
                if(closed.contains(successeur.etat)){

                }

            }
            //Une autre boucle remonte les pointeurs parents.
            iteration++;
        }


        //  --
        //  ---
        //  ---   Calculez s.etat.g = e.g + s.cout.
        //  ---   Vérifiez si s.etat existe dans open.
        //  ----    Si s.etat est déjà dans open, vérifiez son .f.
        //  ---   Ajoutez s.etat dans open si nécessaire.
        //  - Exécutez le programme sur un problème TRÈS simple (ex: prob00.txt)
        //  --  Vérifiez le bon fonctionnement de la génération des états.
        //  --  Vérifiez que e.f soit croissant (>=).
        //  - Réfléchissez maintenant à une heuristique.
        //  - Une fois que l'algorithme et l'heuristique fonctionne :
        //  -- Ajoutez un TreeSet<Etat> open2 avec un comparateur basé sur f.
        //  -- Évaluez la pertinence d'un PriorityQueue.
        //  - Commentez les lignes de traçage (déboggage avec System.out.println).


        // Un plan est une séquence (liste) d'actions. Ici, une actions est tout simplement une String.
        LinkedList<String> plan = new LinkedList<String>();

        // A* ici.

        return plan;
    }

}
