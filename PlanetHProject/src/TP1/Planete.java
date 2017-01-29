package TP1;/*
 * INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * 
 * Hiver 2017 / TP1
 * 
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;


/**
 * Contient des données statiques sur le monde.
 *
 * ATTENTION: vous ne devez pas modifier cette classe.
 *
 */
public class Planete {

    public Map<String, Emplacement>  emplacements = new TreeMap<String, Emplacement>();

    /** Vitesse du Htepien en unités de distance / unités de temps (ex: m/s) */
    public double  vitesse = 1.0;
    
    /** Durée du chargement en unités de temps */
    public double  dureeChargement = 30;
    
    /** Durée du déchargement en unités de temps */
    public double  dureeDechargement = 30;
    
    /** Nombre maximum de bombes que le Htepien peut transporter */
    public int     nbMaxBombes = 1;

    /** Pour simplifier, on limite le nombre de Htepien à 1. Son nom est r0 */
    public String  nomHtepien = "r0";
    
    public ArrayList<String> nomBombes = new ArrayList<String>();

    public double[][]  distances;
    public Emplacement[][] navigationTable;


    public void buildNavigationTable(){
        int n = emplacements.size();
        distances = new double[n][n];
        navigationTable = new Emplacement[n][n];

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                distances[i][j] = Double.MAX_VALUE;
                navigationTable[i][j] = null;
            }

        Vector<Emplacement> vemplacements = new Vector(emplacements.size());
        vemplacements.setSize(emplacements.size());
        {
            for(Emplacement e: emplacements.values()){
                vemplacements.set(e.id, e);
                for(Route r : e.routes)
                {
                    distances[r.origine.id][r.destination.id] = r.getLongueur();
                    navigationTable[r.origine.id][r.destination.id] = r.destination;
                }
            }
        }

        TreeSet<Integer> open = new TreeSet<Integer>();
        for(int i=0;i<n;i++){
            open.add(i);
            navigationTable[i][i] = vemplacements.get(i);
            distances[i][i] = 0.0;
        }

        while(!open.isEmpty()){
            Integer first = (Integer) open.first();
            open.remove(first);
            int i = first.intValue();
            Emplacement l1 = vemplacements.get(i);
            for(Route r : l1.routes){
                int j = r.destination.id;
                for(int k=0;k<n;k++){
                    if(distances[i][j] + distances[i][k] < distances[j][k]){
                        distances[j][k] = distances[i][j] + distances[i][k];
                        navigationTable[j][k] = l1;
                        //navigationTable[k][j] = l2;
                        open.add(new Integer(j));
                        open.add(new Integer(k));
                    }
                }
            }
        }

    }


}
