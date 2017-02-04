package TP1;/*
 * INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * 
 * Hiver 2017 / TP1
 * 
 */

public class But {

    /**
     * Array contenant la destination des colis. Dans le même ordre que dans Monde.java.
     */
    protected Emplacement[] destinationsBombes;

    /**
     * Retourne vrai si le but est satisfait dans l'état passé en paramètre.
     *
     * @param etat
     * @return
     */
    public boolean butEstStatisfait(Etat etat) {
        // Vérification préalable : destinations.length et etat.positionsColis.length est le nombre de colis.
        assert destinationsBombes.length == etat.emplacementsBombes.length;

        // Pour tous les colis, vérifier si sa position courante (état) est celle désiré (but).
        for (int i = 0; i < destinationsBombes.length; i++)
            if (etat.emplacementsBombes[i] != destinationsBombes[i])
                return false;
        return true;
    }

}
