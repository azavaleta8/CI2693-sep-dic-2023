import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DegreesOfSeparation {
    public static void main(String[] args) throws FileNotFoundException {

        // Lee los nombres de las personas de la línea de comandos
        String name1 = args[0];
        String name2 = args[1];

        ArrayList<String> lines = readFile("input.txt");

        // Cremos un nuevo grafo
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        // Recorremos los datos para crear los vertices y las ariste del grafo
        for (String line : lines) {

            String[] name = line.split(" ");

            // Si los nombres no estan en el grafo se agregan
            if (!graph.contains(name[0]))
                graph.add(name[0]);
            if (!graph.contains(name[1]))
                graph.add(name[1]);

            // Creamos las aristas
            graph.connect(name[0], name[1]);
            graph.connect(name[1], name[0]);
        }

        // graph.printAdjacencyList();

        // Calcula el grado de separacion entre las dos personas
        int separation = findSeparation(graph, name1, name2);

        // Imprime el grado de separacion en la salida estándar
        System.out.println(separation);

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
     * Funcioon encaragas de recorrer el grafo mediante BFS desde el nombre1 hasta
     * nombre2
     * Calculando su grado de separacion
     * 
     * @param graph grafo donde estan los datos.
     * @param name1 nombre de la persona 1.
     * @param name2 nombre de la persona 2.
     * @return numero del grado de separacion entre las dos personas.
     */
    private static int findSeparation(AdjacencyListGraph<String> graph, String name1, String name2) {

        // Si alguno de los dos nombre no pertenece al grafo retornamos -1
        if (!graph.contains(name1) || !graph.contains(name2)) {
            return -1;
        }

        // Si los nombres son el mismo retornamos 0
        if (name1.equals(name2)) {
            return 0;
        }

        // Creamos una cola para almacenar las personas que no se han visitado
        List<String> queue = new ArrayList<>();
        queue.add(name1);

        // Creamos un set para almacenar las personas que ya se han visitado
        Set<String> visited = new HashSet<>();
        visited.add(name1);

        // Inicializamos el grado de separacion
        int separation = 1;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String vertex = queue.remove(0);

                // Obtenemos los vertices adjacentes
                List<String> verticesConnectedTo = graph.getVerticesConnectedTo(vertex);

                // Visita los vecinos del vertice actual
                for (String neighbor : verticesConnectedTo) {

                    if (!visited.contains(neighbor)) {

                        // Si el vecino es la persona objetivo, se ha encontrado el grado de separacion
                        if (neighbor.equals(name2)) {
                            return separation;
                        }

                        // Agrega el vecino a la cola y al array de visitados
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            // Se incrementa el grado de separacion
            separation++;
        }

        // Si no se encontro el grado de separacion, se devuelve -1
        return -1;
    }

}
