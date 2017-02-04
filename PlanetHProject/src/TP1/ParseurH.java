package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


public class ParseurH {
    protected Planete planete;
    protected Etat etatInitial;
    protected But but;

    public void parse(String nomFichier) throws IOException {
        FileInputStream in = null;
        try {
            File inputFile = new File(nomFichier);
            in = new FileInputStream(inputFile);
        } catch (Exception e) {
            return;
        }
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        BufferedReader bin2 = new BufferedReader(new InputStreamReader(in));
        String input;
        input = bin.readLine();
        int NbreLignes = Integer.parseInt(input);
        input = bin.readLine();
        int NbreColonnes = Integer.parseInt(input);
        String nomCol = "";

        planete = new Planete();
        etatInitial = new Etat(planete);
        Vector<String> positionBombes = new Vector();
        Map<String, Emplacement> destinations = new TreeMap();
        //création des emplacement
        int n = 0;
        for (int Y = 0; Y < NbreLignes; Y++) {
            input = bin.readLine();
            for (int X = 0; X < NbreColonnes; X++) {
                if (input.charAt(X) != ' ') {
                    String name = Y + "-" + X;
                    Emplacement location = new Emplacement(name, X,Y, "" + input.charAt(X));
                    planete.emplacements.put(name, location);
                    if (input.charAt(X) == 'E') etatInitial.emplacementHtepien = planete.emplacements.get(name);
                    if (input.charAt(X) == 'B') {
                        planete.nomBombes.add("" + input.charAt(X));
                        positionBombes.add(name);
                    }
                    if (input.charAt(X) == 'S') {
                        Emplacement e = planete.emplacements.get(name);
                        if (e == null) throw new RuntimeException();
                        planete.setSortie(e);
                        destinations.put(name, e);
                        nomCol = name;
                    }
                }
            }
        }
        int nbBombes = positionBombes.size();
        //création des routes

        for (int i = 0; i < NbreLignes; i++) {
            for (int j = 0; j < NbreColonnes; j++) {
                if (planete.emplacements.get(i + "-" + j) != null) {
                    Emplacement l1 = planete.emplacements.get(i + "-" + j);
                    if (planete.emplacements.get(i + "-" + (j - 1)) != null) {
                        Emplacement l2 = planete.emplacements.get(i + "-" + (j - 1));
                        l1.routes.add(new Route(l1, l2));
                        // l2.routes.add(new Route(l2, l1));
                    }
                    if (planete.emplacements.get((i - 1) + "-" + j) != null) {
                        Emplacement l2 = planete.emplacements.get((i - 1) + "-" + j);
                        l1.routes.add(new Route(l1, l2));
                        //  l2.routes.add(new Route(l2, l1));
                    }
                    if (planete.emplacements.get(i + "-" + (j + 1)) != null) {
                        Emplacement l2 = planete.emplacements.get(i + "-" + (j + 1));
                        l1.routes.add(new Route(l1, l2));
                        //  l2.routes.add(new Route(l2, l1));
                    }
                    if (planete.emplacements.get((i + 1) + "-" + j) != null) {
                        Emplacement l2 = planete.emplacements.get((i + 1) + "-" + j);
                        l1.routes.add(new Route(l1, l2));
                        // l2.routes.add(new Route(l2, l1));
                    }


                }
            }
        }
        etatInitial.bombesCharges = new boolean[nbBombes];
        etatInitial.emplacementsBombes = new Emplacement[nbBombes];
        for (int i = 0; i < positionBombes.size(); i++) {
            if (positionBombes.get(i).equals(planete.nomHtepien)) {
                etatInitial.bombesCharges[i] = true;
                etatInitial.nbbombesCharges++;
            } else {
                etatInitial.emplacementsBombes[i] = planete.emplacements.get(positionBombes.get(i));
                if (etatInitial.emplacementsBombes[i] == null)
                    throw new RuntimeException();
            }
        }
        but = new But();
        but.destinationsBombes = new Emplacement[nbBombes];
        for (int i = 0; i < nbBombes; i++)
            but.destinationsBombes[i] = destinations.get(nomCol);

    }

}
