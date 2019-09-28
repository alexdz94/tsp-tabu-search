import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class launcher {
    public static void main(String[]args)
    {
       JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(new JFrame());
        File inputFile = fileChooser.getSelectedFile();
        System.out.println("Selected file : "+inputFile.getAbsolutePath());
        ArrayList<City> cities = new ArrayList<City>();
        try{
            Scanner input = new Scanner(inputFile);
            while (input.hasNextLine())
            {
                String line = input.nextLine();
                String[] line_array = line.split(",");
                cities.add(new City(Double.parseDouble(line_array[0]),Double.parseDouble(line_array[1]),line_array[2]));
            }
        }catch (Exception e)
        {
            System.out.println("Error : "+e.getMessage() +" !");
        }
        /*
        * Jusqu'ici, c'est la lecture du fichier et le remplissage de la liste des villes
        * */
        Route route = new Route(cities);
        // Initialisation de la premiére solution
        tabuSearchAlgorithm tabuSearch = new tabuSearchAlgorithm();
        tabuSearch.findShortestRoute(route);
        // Lancement de l'algorithme avec la solution initiale
    }
}
