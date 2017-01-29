package TP1;/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

/**
 * Point d'entrée du programme.
 */
public class TP1 {

    public static void main(String args[])
            throws IOException
    {
        ParseurH parseur = new ParseurH();
        

        BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
		String line = "planeteH01.txt";
		System.out.println("Donne le nom du fichier contenant ton mondeH.");
                System.out.println("Remarque: le fichier doit être à la racine du projet et dois se terminer par .txt");
                System.out.println("Exemple: planeteH01.txt");
		/*try{
			line=keyboard.readLine();//Recup du texte
		}catch(IOException e){
			System.out.println("err");
		}*/
        parseur.parse(line);
        parseur.planete.buildNavigationTable();
        // Création de l'objet évaluateur d'heuristique
        Heuristique h = new Heuristique(parseur.planete);
        
        // Appel à l'algorithme A*
        List<String> plan = AStar.genererPlan(parseur.etatInitial, parseur.but, h);

        // Affichage ou écriture du plan
        PrintStream out;
        if(args.length>1)
            out = new PrintStream(args[1]);
        else
            out = System.out;
        File f = new File("planH"+line.substring(8, 10)+".txt");
        try{
            FileWriter fw = new FileWriter(f);
        out.println("Plan {");
        fw.write("Plan  { \n");
        fw.flush();
        for(String action : plan)
            {
              out.println(action + ";"); 
              fw.write(action + "; \n");
              fw.flush();
            }
        out.println("}");
        fw.write("}");
        fw.close();
           }catch(IOException e){
			System.out.println("err");
           }
            


    }

}
