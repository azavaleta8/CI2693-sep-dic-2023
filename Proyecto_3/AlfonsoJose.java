import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AlfonsoJose {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> lines = readFile("atlantis.txt");

        int[][] city = parseCityData(lines);
        int waterCubes = calculateWaterCubes(city);

        System.out.println(waterCubes);
    }

    /**
     * Funcion encargada de leer el archivo y guardar los datos en un array para
     * 
     * @param filename Nombre del archivo a leer.
     * @return Lista con los datos contenido dentro del archivo.
     */
    public static ArrayList<String> readFile(String filename) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();

        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            lines.add(data);
        }

        myReader.close();

        return lines;
    }

    /**
     * Funcion encargada de convertir los datos de la ciudad en formato de matriz de
     * alturas.
     *
     * @param lines Lista de Strings con los datos de la ciudad.
     * @return Matriz de alturas de la ciudad.
     */
    public static int[][] parseCityData(ArrayList<String> lines) {
        int rows = lines.size();
        int columns = lines.get(0).split("\\s+").length;

        int[][] city = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            String[] heights = lines.get(i).trim().split("\\s+");
            for (int j = 0; j < columns; j++) {
                city[i][j] = Integer.parseInt(heights[j]);
            }
        }

        return city;
    }

    /**
     * Funcion encargada de calcular la cantidad de cubos de agua necesarios para
     * llenar la ciudad.
     *
     * @param city Matriz de alturas de la ciudad.
     * @return Cantidad de cubos de agua necesarios.
     */
    public static int calculateWaterCubes(int[][] city) {
        int rows = city.length;
        int columns = city[0].length;
        int[][] maxHeights = new int[rows][columns];
        int waterCubes = 0;

        // Calcular alturas máximas por fila
        for (int i = 0; i < rows; i++) {
            int maxHeight = 0;
            for (int j = 0; j < columns; j++) {
                maxHeight = Math.max(maxHeight, city[i][j]);
                maxHeights[i][j] = maxHeight;
            }
        }

        // Calcular alturas máximas por columna
        for (int j = 0; j < columns; j++) {
            int maxHeight = 0;
            for (int i = 0; i < rows; i++) {
                maxHeight = Math.max(maxHeight, city[i][j]);
                maxHeights[i][j] = Math.min(maxHeights[i][j], maxHeight);
            }
        }

        // Calcular cubos de agua necesarios
        // Como los bordes no pueden contener agua no se cuentan
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                waterCubes += Math.max(0, maxHeights[i][j] - city[i][j]);
            }
        }

        return waterCubes;
    }

}