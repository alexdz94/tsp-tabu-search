import java.util.ArrayList;

public class tabuSearchAlgorithm {
    // La liste tabou qui va contenir les derniéres solutions explorées
    public static ArrayList<Integer> tabuList = new ArrayList<Integer>();
    // Taille maximale de la liste
    public static int tabuListMaxSize = 10;
    // Nombre d'itérations a chaque fois
    public static int iterations = 200;
    // Condition d'arrêt de l'algorithme, jusqu'a 70 x itérations
    public static int stopCondition = 70;

    /*
    * Cette méthode prend une solution initiale aléatoire et commence à explorer un voisinage pour trouver mieux
    * A la fin de chaque 200 itérations, on considére qu'on a trouvé la meilleur solution dans ce voisinage
    * On garde la solution comme global si elle est meilleur que l'actuelle et on reprend le même processus
    * Si on trouve qu'une solution est déja visité, on prend une autre solution (même si mauvaise) pour donner plus
    * de chance a tomber sur le minimum global
    * à la fin, on peut considérer que le plus petit élément dans la liste tabou est le minimum global approché
    * */
    public void findShortestRoute(Route currentRoute)
    {
        Route candidateRoute; // une solution du voisinage
        Route bestCandidate; // meilleure solution dans un voisinage
        int stopConditionCounter = 0;
        bestCandidate = new Route(currentRoute);
        System.out.println("     "+(int)currentRoute.getFullRouteDistance()+" Km       |         Initial");
        while(stopConditionCounter < stopCondition)
        {
            bestCandidate = getNeighborhoodSolution(new Route(bestCandidate));
            for(int index = 0 ; index < iterations ; index++)
            {
                candidateRoute = getNeighborhoodSolution(new Route(bestCandidate));
                if(!tabuList.contains((int)candidateRoute.getFullRouteDistance())) {
                    if(candidateRoute.getFullRouteDistance() < bestCandidate.getFullRouteDistance()){
                        bestCandidate = new Route(candidateRoute);
                        System.out.println("     "+(int)bestCandidate.getFullRouteDistance()+" Km       |         Better ");
                    }
                }else{
                    System.out.println("     "+(int)candidateRoute.getFullRouteDistance()+" Km       |         Exist in Tabu List <- Not selected");
                }
            }
            System.out.println("      Local        |        Finished ");
            if(tabuList.size() >= tabuListMaxSize) tabuList.remove(0); // pour respecter la taille max de la liste tabou
            if((int)bestCandidate.getFullRouteDistance() < (int)currentRoute.getFullRouteDistance())
            {
                // Si la meilleure solution du voisinage mieux que l'actuelle, on mis a jour la meilleure solution
                // et on l'ajoute dans la liste tabou.
                currentRoute=new Route(bestCandidate);
                if(!tabuList.contains((int)currentRoute.getFullRouteDistance()))
                    tabuList.add((int)currentRoute.getFullRouteDistance());
            }
            stopConditionCounter++;

        }
        System.out.println();
        System.out.println("------- Finished By Stop Condition ------");
        System.out.println();
        System.out.println("Tabu List Size : "+tabuList.size());
        System.out.print("Locals Minimum : ");
        for(int dist : tabuList)
        {
            System.out.print(dist+" km | ");
        }
        System.out.println("\n");
        System.out.println("------- Approached Global Minimum ------");
        System.out.println();
        System.out.println(getMinimum()+" Km       |         Final");
    }

    public Route getNeighborhoodSolution(Route aRoute)
    {
        int random1 = 0 ;
        int random2 = 0 ;
        /* On choisis 2 villes différentes pour les échanger
           à partir du 0 jusqu'a la taille de la liste (nombre de villes)
           On choisissant 2 villes aléatoirement, et on les echange (swap) !
        */
        while(random1==random2){
            random1 = (int) (aRoute.cities.size()* Math.random());
            random2 = (int) (aRoute.cities.size()* Math.random());
        }
        City city_1 = aRoute.cities.get(random1);
        City city_2 = aRoute.cities.get(random2);
        aRoute.cities.set(random2,city_1);
        aRoute.cities.set(random1,city_2);
        return aRoute;
    }

    /*
    * Retourne le minimum d'une liste
    * */
    public int getMinimum()
    {
        int minimum = tabuList.get(0);
        for(int dist : tabuList)
        {
            if(dist<=minimum) minimum = dist;
        }
        return minimum;
    }

}
