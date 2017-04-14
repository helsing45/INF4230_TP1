### TP1 : Algorithme A* pour sauver la planète H.(Hiver 2017)
## 1. Objectifs
- Comprendre la notion d'espace d'états.
- Expérimenter de paradigme résolution de problèmes en IA.
- Implémenter une fonction successeur (fonction de transition).
- Implémenter l'algorithme A*.
- Appliquer l'algorithme A* à la résolution d'un problème.
- Définir et implémenter une heuristique admissible efficace.
 
## 2. La planète H

Vous devez écrire un programme tp1 qui détermine les actions nécessaires à effectuer par un Htepien (habitant de la planète H) pour évacuer une bombe menaçant la destruction de la planète.

**Histoire** : Les Htepiens vivaient heureux jusqu’au jour où un méchant bonhomme a décidé de placer des bombes sur la planète. Le nom « H » vient du fait que chaque configuration de la planète (monde)  a toujours la forme d’un H (monde H). Le but est donc d’évacuer toutes les bombes situées dans une configuration de cette planète avant qu’elles n’explosent. Chaque monde H a un emplacement (ou lieu) d’entrée E et un emplacement de sortie S. Il possède aussi un ou plusieurs ponts (constitués d’emplacements de type P) qui relient les deux parties du monde. Lorsqu’il n’est pas occupé, le Htepien peut l’emprunter pour passer d’une partie du monde à l’autre. Pour évacuer une bombe d’un monde H, un Htepien doit entrer dans le monde par E, et se déplacer (passer d’un emplacement à l’autre) vers la bombe (emplacement B), la prendre, la ramener (se déplacer) vers et la déposer à la sortie S.  Si seulement sauver la planète H était si simple L !

En effet, le méchant bonhomme n’a pas laissé que des bombes, mais partout où il est passé, il a laissé des emplacements épineux marqués ‘-‘ (ou rouge lorsque visualisés) ralentissant le parcours des Htepiens (les emplacements normaux sont représentés par le symbole # (ou blanc)). Heureusement, Emily, la reine de la planète, a réussi à transformer certains emplacements épineux en emplacements magiques ‘+’ (ou vert) qui accélèrent le parcours des Htepiens une fois à l’intérieur (emplacements de coût nul). Ainsi, le Htepien qui doit sauver la planète doit en se déplaçant, éviter (lorsque possible) les emplacements rouges et profiter (lorsque possible) des emplacements verts, tout en passant le moins de temps possible pour éviter que la bombe n’explose, entraînant la planète avec.

**Formalisation** : Le Htepien se déplace sur une carte du monde H pour récupérer et aller déposer la bombe à la sortie. On suppose que chaque monde H est une grille 2D (en forme H). La carte est constituée d'un ensemble d’emplacements et de routes (chemins d’un emplacement à un autre emplacement voisin). Chaque emplacement a un nom (« i-j » pour un emplacement situé à la ligne i+1 et la colonne j+1) et une coordonnée (X,Y) qui n’est autre que (i,j). Une route est spécifiée par une paire de noms d’emplacement. Les routes sont bidirectionnelles (aucun sens unique). La longueur d'une route est toujours de 1 car elle sépare deux emplacements voisins (chaque emplacement a au plus 4 voisins situés au Nord, au Sud, à l’Est et à l’Ouest). L'état initial est spécifié par les positions du Htepien et de la bombe. Le but est spécifié de la façon suivante: pour chaque bombe devant être déplacée, on donne son nom et l’emplacement de sa destination (la sortie du monde H). Le Htepien peut transporter un maximum d’une seule bombe en même temps. Normalement, il peut y avoir plusieurs bombes et plusieurs sorties dans un monde H. Cependant pour faciliter ce premier TP, on supposera qu’il y a une seule bombe et une seule sortie.

## 3. Système intelligent
### 3.1. Caractéristiques de l’agent
- Agent : un Htepien.
- Environnement : la carte du monde H et la bombe.
- Mesure de performance : temps requis pour trouver la bombe et la déplacer la bombe jusqu’à la sortie.
- Actions : Nord, Sud, Est, Ouest, charger et décharger.
- Capteurs : localisateurs pour connaître la position et le type d’emplacement courant et voisins (+, -, #, P, E, B, S ou vide).
 
 
### 3.2. Caractéristiques de l’Environnement
- Complètement observable.
- Déterministe.
- Séquentiel.
- Statique.
- Discret.
- Agent unique.
 
 
## 4. Spécification du problème

### 4.1. Entrée
Dans le cadre du TP1, il y aura toujours un seul Htepien nommé r0.

Voici à titre d'exemple le fichier planeteH01.txt qui représente une configuration d’un monde de la planète H (monde H).

5
12
E##+    ####
###-    #+B#
+# #PPPP# ##
++-#    #-+-
S###    -##+
 

**Légende** :

- La 1ere ligne correspond au nombre de lignes de la grille.
- La 2eme ligne correspond au nombre de colonnes de la grille.
- «**E** » correspond à l’entrée (emplacement de départ).
- «**B** » correspond à l’emplacement de la bombe
- «**S** » correspond à la sortie (couleur rose sur la carte)
- « + » correspond à un emplacement magique (couleur vert sur la carte)
- « - » correspond à un emplacement épineux (rouge)
- «  » l’espace correspond à des emplacements inaccessibles (noir)
- « **P** » correspond à une case du pont.

L’objectif est de minimiser le coût d’évacuation. La planeteH01 ci-dessus correspond à la carte ci-dessous (visible avec **SimH** le simulateur de la planète H) :



### 4.2  Actions

Le tableau suivant décrit les types d'action dans un monde H.

| Actions |Description|Durée (Coût)|
|---------|-----------|------------|
|Nord     |Aller vers l’emplacement situé au Nord |1 (+ le coût de l’emplacement cible)|
|Sud| Aller vers l’emplacement situé au Sud|1 (+ le coût de l’emplacement cible)|
|Ouest|Aller vers l’emplacement situé à l’Ouest|1 (+ le coût de l’emplacement cible)|
|Est|Aller vers l’emplacement situé à l’Est|1 (+ le coût de l’emplacement cible)|
|Charger|Charger la bombe (prendre la bombe)|30|
|Décharger|Décharger la bombe (déposer la bombe)|30|

 
### 4.3 Solution
Une solution (plan) à un problème d’évacuation d’une bombe dans une ville de la planète H, est une séquence d'actions.

La mesure de performance pour ce problème est le coût (coût total du plan = somme des coûts de déplacement incluant les coûts reliés aux emplacements cibles,  sachant que les emplacements  rouges  (épineux) augmentent de le coût de déplacement de 2, les emplacements verts (magiques) de 0 et les emplacements blancs (normaux) de 1. Une solution optimale est une solution qui minimise le coût total.

### 4.4 Sortie
La sortie du programme est un plan. Voici le plan planH01.txt pour résoudre le problème précédent planeteH01.txt.

 
 
Plan  { 
Est = Lieu 0-0 -> Lieu 0-1); 
Est = Lieu 0-1 -> Lieu 0-2); 
Est = Lieu 0-2 -> Lieu 0-3); 
Sud = Lieu 0-3 -> Lieu 1-3); 
Sud = Lieu 1-3 -> Lieu 2-3); 
Est = Lieu 2-3 -> Lieu 2-4); 
Est = Lieu 2-4 -> Lieu 2-5); 
Est = Lieu 2-5 -> Lieu 2-6); 
Est = Lieu 2-6 -> Lieu 2-7); 
Est = Lieu 2-7 -> Lieu 2-8); 
Nord = Lieu 2-8 -> Lieu 1-8); 
Est = Lieu 1-8 -> Lieu 1-9); 
Est = Lieu 1-9 -> Lieu 1-10); 
Charger(r0,B); 
Ouest = Lieu 1-10 -> Lieu 1-9); 
Ouest = Lieu 1-9 -> Lieu 1-8); 
Sud = Lieu 1-8 -> Lieu 2-8); 
Ouest = Lieu 2-8 -> Lieu 2-7); 
Ouest = Lieu 2-7 -> Lieu 2-6); 
Ouest = Lieu 2-6 -> Lieu 2-5); 
Ouest = Lieu 2-5 -> Lieu 2-4); 
Ouest = Lieu 2-4 -> Lieu 2-3); 
Sud = Lieu 2-3 -> Lieu 3-3); 
Ouest = Lieu 3-3 -> Lieu 3-2); 
Ouest = Lieu 3-2 -> Lieu 3-1); 
Ouest = Lieu 3-1 -> Lieu 3-0); 
Sud = Lieu 3-0 -> Lieu 4-0); 
Decharger(r0,B); 
}
 
 

Ce plan, visualité dans SimH correspondra à ceci :



 

### 4.5 Utilisation du Simulateur

Pour simuler visuellement des plans, utilisé SimH selon les instructions suivantes :

Téléchargez SimH.jar.
Assurez-vous de mettre les plans et les instances de planètes H (planH0X, planeteH0X) dans le même répertoire que SimH.jar.
Lancez SimH.jar (java -jar SimH.jar).
Choisissez un monde H (préalablement créé). Ex : planeteH01.txt
Choisissez le plan correspondant. Ex : planH01.txt
Chargez le monde et le plan.
Lancez la simulation.
Effacez la simulation avant d’en lancer une autre.
Remarque : Si vous créez votre propre monde H (utilisez le nom de fichier ‘planeteH07.txt’). Assurez-vous que ce fichier et le plan correspondant généré par votre TP1 (‘planH07.txt’)  soient dans le bon répertoire afin de pouvoir les sélectionner dans SimH pour des fins de visualisation.

 

## 5 Squelette de départ, Fichiers tests et Recommandations
### 5.1 Fichiers

Bien qu'il soit optionnel, il est fortement recommandé de commencer le TP1 à l'aide du squelette TP1.zip écrit en Java.

Des problèmes (tests) planeteH01.txt à planeteH06.txt sont également fournis dans TP1.zip.

### 5.2 Tâches recommandées

Élaborez deux heuristiques admissibles : chaque heuristique est une sous-classe de la classe Heuristique utilisée par défaut avec une valeur heuristique de 0 pour tous les nœuds. Vous devez redéfinir la méthode estimerCoutRestant() pour vos heuristiques.
Testez.
Suivez les recommandations données dans les fichiers sources du squelette.
### 5.3 Conseils techniques

L'algorithme A* a besoin:

de choisir le prochain état à visiter (le plus prometteur selon f dans open).
de vérifier si un état a déjà été visité (dans closed).
Pour implémenter la «liste» open, on peut utiliser un objet de la classe TreeSet ou PriorityQueue. Attention : il faut créer un Comparator pour trier selon f.

Pour implémenter la «liste» closed, on peut utiliser un objet de la classe TreeSet ou HashSet .

### 5.4 Taille des équipes

Vous pouvez faire ce travail en équipe  d’au plus 3. Toutefois, tous les membres de l'équipe doivent contribuer à l'ensemble du travail et non à seulement quelques parties. Le travail d'équipe vise à favoriser les discussions et l'entraide. Le travail d'équipe ne vise pas à réduire la tâche. Ainsi, se diviser la tâche en deux n'est pas une méthode de travail d'équipe appropriée dans ce cours. La participation inadéquate des membres de l'équipe peut être considérée comme du plagiat. Le professeur et le correcteur pourront sélectionner quelques équipes au hasard afin de vérifier que tous les membres sont capables d'expliquer l'ensemble du travail.

 

## 6 Contraintes
 
### 6.1 Environnement d'exécution

Limite de mémoire : 1Go. Cette limite est spécifiée avec java -Xmx1g en Java et ulimit en C++.

### 6.2 Langage de programmation
Java

Un seul fil d'exécution (thread) est permis.

