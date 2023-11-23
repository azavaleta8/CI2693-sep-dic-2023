import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NextToYou {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> lines = readFile("Caracas.txt");

        // Leemos las lineas del archivo
        Map<String, Integer> nameToIndex = createNameToIndexMap(lines);

        // Obtenemos la cantidad de ciudades e inicializamos el grafo
        int n = nameToIndex.size();
        int[][] graph = new int[n][n];

        // Recorre los datos para crear el grafo
        for (String line : lines) {

            String[] parts = line.split(",");
            String source = parts[0].trim();
            String destination = parts[1].trim();
            int sourceIndex = nameToIndex.get(source);
            int destinationIndex = nameToIndex.get(destination);

            graph[sourceIndex][destinationIndex] = 1;
        }

        // Encontramos los SCC e inizialimos variales para contar la cantidad de
        // localidades
        int[] components = StronglyConnectedComponents(graph);
        int smallLocalityCount = 0;
        int mediumLocalityCount = 0;
        int largeLocalityCount = 0;

        // Contamos el tamano de cada SCC
        Map<Integer, Integer> localitySizes = countStronglyConnectedComponentSize(components);

        // Clasificamos los SCC segun la cantidad de comercioens que tienen
        for (int localitySize : localitySizes.values()) {

            if (localitySize <= 2) {
                smallLocalityCount++;
            } else if (localitySize <= 5) {
                mediumLocalityCount++;
            } else {
                largeLocalityCount++;
            }
        }

        // Calculamos el numero de deliverys necesarios
        int totalDeliverys = smallLocalityCount * 10 + mediumLocalityCount * 20 + largeLocalityCount * 30;
        System.out.println(totalDeliverys);

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
     * Funcion encargada de mapear las conexciones de la cuidades
     * para saber cuandos nodos van a haber
     * 
     * @param lines String con los lines del archivo.
     * @return hashmap con el mapeado ya hecho de cada nodo con un indice.
     */
    public static Map<String, Integer> createNameToIndexMap(ArrayList<String> lines) {
        Map<String, Integer> nameToIndex = new HashMap<>();
        int index = 0;

        for (String line : lines) {
            String[] parts = line.split(",");
            String source = parts[0].trim();
            String destination = parts[1].trim();

            if (!nameToIndex.containsKey(source)) {
                nameToIndex.put(source, index);
                index++;
            }

            if (!nameToIndex.containsKey(destination)) {
                nameToIndex.put(destination, index);
                index++;
            }
        }

        return nameToIndex;
    }

    /**
     * Aplica el algoritmo de RoyWarshall para determinar la conectividad entre
     * nodos del grafo.
     * 
     * @param graph Matriz que representa el grafo.
     * @return Una matriz booleana donde el valor en la posición i,j indica si hay
     *         un camino de i a j en el grafo.
     */
    public static boolean[][] RoyWarshall(int[][] graph) {

        int n = graph[0].length;
        boolean[][] reachable = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || graph[i][j] != 0) {
                    reachable[i][j] = true;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {

                if (i == k || !reachable[i][k]) {
                    continue;
                }

                for (int j = 0; j < n; j++) {
                    reachable[i][j] = reachable[i][j] || reachable[k][j];
                }

            }
        }
        return reachable;
    }

    /**
     * Encuentra los componentes fuertemente conectados del grafo
     * 
     * @param graph Matriz que representa el grafo.
     * @return Arreglo de enteros que asigna un componente a cada nodo del grafo.
     */
    public static int[] StronglyConnectedComponents(int[][] graph) {

        boolean[][] reachable = RoyWarshall(graph);
        int n = graph[0].length;
        int[] components = new int[n];

        for (int i = 0; i < n; i++) {
            components[i] = -1;
        }

        for (int v = 0; v < n; v++) {

            if (components[v] != -1) {
                continue;
            }

            components[v] = v;

            for (int w = 0; w < n; w++) {
                if (reachable[v][w] && reachable[w][v]) {
                    components[w] = v;
                }
            }

        }
        return components;
    }

    /**
     * Cuenta el tamaño de cada componente fuertemente conectado en un grafo.
     * 
     * @param components Arreglo que asigna un componente a cada nodo del grafo.
     * @return hashmap donde la clave es el número del componente y el valor es el
     *         tamaño correspondiente.
     */
    public static Map<Integer, Integer> countStronglyConnectedComponentSize(int[] components) {
        Map<Integer, Integer> componentCounts = new HashMap<>();

        for (int component : components) {
            if (componentCounts.containsKey(component)) {
                int count = componentCounts.get(component);
                componentCounts.put(component, count + 1);
            } else {
                componentCounts.put(component, 1);
            }
        }

        return componentCounts;
    }

}