import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Arbitrage {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> lines = readFile("tasas.txt");

        // Leemos las lineas del archivo
        Map<String, Integer> currencyToIndex = createCurrencyToIndexMap(lines);

        // Obtenemos la cantidad de monedas e inicializamos el grafo
        int n = currencyToIndex.size();
        double[][] graph = new double[n][n];

        // Recorre los datos para crear el grafo
        for (String line : lines) {

            String[] parts = line.split(" ");
            String source = parts[0].trim();
            String destination = parts[1].trim();
            double weight = Double.parseDouble(parts[2].trim());
            int sourceIndex = currencyToIndex.get(source);
            int destinationIndex = currencyToIndex.get(destination);

            graph[sourceIndex][destinationIndex] = weight;
        }

        // Realizar el algoritmo de Bellman-Ford
        boolean hasArbitrage = bellmanFord(graph);

        if (hasArbitrage) {
            System.out.println("DINERO FACIL DESDE TU CASA");
        } else {
            System.out.println("TODO GUAY DEL PARAGUAY");
        }

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
    public static Map<String, Integer> createCurrencyToIndexMap(ArrayList<String> lines) {
        Map<String, Integer> currencyToIndex = new HashMap<>();
        int index = 0;

        for (String line : lines) {
            String[] parts = line.split(" ");
            String source = parts[0].trim();
            String destination = parts[1].trim();

            if (!currencyToIndex.containsKey(source)) {
                currencyToIndex.put(source, index);
                index++;
            }

            if (!currencyToIndex.containsKey(destination)) {
                currencyToIndex.put(destination, index);
                index++;
            }
        }

        return currencyToIndex;
    }

    /**
     * Funcion bellmanFord modificada para encontrar un ciclo donde el dinero
     * aumente y sea posible hacer arbitraje
     * 
     * @param graph Grafo donde estas las nodos y las aristas ponderadas
     * @return bolean indica si es posible hacer arbitraje a traves de un ciclo.
     */
    public static boolean bellmanFord(double[][] graph) {
        int n = graph.length;
        double[] dist = new double[n];
        int src = 0;

        // Inicializar distancias
        for (int i = 0; i < n; ++i) {
            dist[i] = graph[src][i];
        }

        // Usamos el 1 ya que es netruo para la multiplicacion
        dist[src] = 1;

        // Relajar las aristas repetidamente
        // done evaluamos si al pasar por el nodo el monto aunmenta
        // y si aumenta se actualiza

        for (int u = 0; u < n; ++u) {
            for (int v = 0; v < n; ++v) {

                double weight = graph[u][v];

                if (weight == 0) {
                    continue;
                }

                if (dist[u] * weight > dist[v]) {
                    dist[v] = dist[u] * weight;
                }

            }
        }

        // Buscando ciclos, si luego de haber relajado las aristas encontramos un ciclo
        // donde se aumente el dinero es decier que es posbile hacer arbitraje
        for (int u = 0; u < n; ++u) {
            for (int v = 0; v < n; ++v) {

                double weight = graph[u][v];

                if (weight == 0) {
                    continue;
                }

                if (dist[u] * weight > dist[v]) {
                    return true;
                }
            }
        }

        return false;

    }

}