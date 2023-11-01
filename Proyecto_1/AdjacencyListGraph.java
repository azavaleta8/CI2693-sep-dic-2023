import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph<T> implements Graph<T> {

    private Map<T, List<T>> adjacencyList;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Agrega un nuevo vertice al grafo.
     *
     * @param vertex El vertice que se agregará al grafo.
     * @return True si el vertice se agrego con éxito, False en caso contrario.
     */
    public boolean add(T vertex) {
        // Verifica si el vertice ya existe en el grafo.
        if (this.contains(vertex)) {
            return false;
        }

        adjacencyList.put(vertex, new ArrayList<>());
        return true;
    }

    /**
     * Agrega una arista del vertice from al vertice to.
     *
     * @param from El vertice desde el que se dirige la arista.
     * @param to   El vertice al que se dirige la arista.
     * @return True si la arista se agrego, False en caso contrario.
     */
    public boolean connect(T from, T to) {

        if (from == to) {
            return false;
        }

        if (!this.contains(from) || !this.contains(to)) {
            return false;
        }

        List<T> successors = adjacencyList.get(from);

        if (successors.contains(to)) {
            return false;
        }

        successors.add(to);
        return true;
    }

    /**
     * Elimina la arista del vertice from al vertice to.
     *
     * @param from El vertice desde el que se dirige la arista.
     * @param to   El vertice al que se dirige la arista.
     * @return True si la arista se elimino, False en caso contrario.
     */
    public boolean disconnect(T from, T to) {

        if (from == to) {
            return false;
        }

        if (!this.contains(from) || !this.contains(to)) {
            return false;
        }

        List<T> successors = adjacencyList.get(from);
        if (!successors.contains(to)) {
            return false;
        }

        successors.remove(to);
        return true;
    }

    /**
     * Devuelve True si el grafo contiene el vertice, False en caso contrario.
     *
     * @param vertex El vertice a buscar.
     * @return True si el vertice esta, False en caso contrario.
     */
    public boolean contains(T vertex) {
        if (adjacencyList.containsKey(vertex)) {
            return true;
        }
        return false;
    }

    /**
     * Devuelve una lista con los vertices que tienen una arista dirigida hacia to.
     *
     * @param to El vertice del que se obtendrán las aristas entrantes.
     * @return Una lista con los vertices.
     */
    public List<T> getInwardEdges(T to) {

        if (!this.contains(to)) {
            return null;
        }

        List<T> inwardEdges = new ArrayList<>();

        // Recorre la lista de adyacencias.
        for (T vertex : adjacencyList.keySet()) {

            // Verifica si esta en la lista de sucesores y si esta lo agrega.
            if (adjacencyList.get(vertex).contains(to)) {
                inwardEdges.add(vertex);
            }
        }

        return inwardEdges;
    }

    /**
     * Devuelve una lista con los vertices a los que from tiene una arista dirigida.
     *
     * @param from El vertice del que se obtendrán las aristas salientes.
     * @return Una lista con los vertices.
     */
    public List<T> getOutwardEdges(T from) {

        if (!this.contains(from)) {
            return null;
        }

        // Obtiene la lista de sucesores del vertice from.
        List<T> successors = adjacencyList.get(from);

        if (successors == null) {
            return null;
        }

        return successors;
    }

    /**
     * Devuelve una lista con los vértices adyacentes.
     *
     * @param vertex El vertice del que se obtendrán los adyacentes.
     * @return Una lista con los vertices adyacentes.
     */
    public List<T> getVerticesConnectedTo(T vertex) {

        if (!this.contains(vertex)) {
            return null;
        }

        List<T> connectedVertices = new ArrayList<>();

        for (T to : getOutwardEdges(vertex)) {
            connectedVertices.add(to);
        }

        for (T from : getInwardEdges(vertex)) {
            connectedVertices.add(from);
        }

        return connectedVertices;
    }

    /**
     * Devuelve la lista de todos los vertices del grafo.
     *
     * @return La lista de todos los vertices del grafo.
     */
    public List<T> getAllVertices() {
        // Devuelve la lista de claves de la lista de adyacencias.
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Elimina el vertice del grafo.
     *
     * @param vertex El vertice a eliminar.
     * @return True si el vertice se eliminó, False en caso contrario.
     */
    public boolean remove(T vertex) {

        if (!this.contains(vertex)) {
            return false;
        }

        // Elimina el vertice de la lista de adyacencias.
        adjacencyList.remove(vertex);

        // Elimina el vertice de la lista de sucesores de todos los vertices.
        for (List<T> successors : adjacencyList.values()) {
            successors.remove(vertex);
        }

        return true;
    }

    /**
     * Devuelve la cantidad de vertices que contiene el grafo.
     *
     * @return La cantidad de vertices que contiene el grafo.
     */
    public int size() {
        return adjacencyList.size();
    }

    /**
     * Devuelve una subgrafo del grafo original que contiene solo los vertices
     * especificados en la coleccion.
     *
     * @param vertices La colección de vertices que se incluirán en el subgrafo.
     * @return Un subgrafo que contiene solo los vertices.
     */
    public AdjacencyListGraph<T> subgraph(Collection<T> vertices) {
        AdjacencyListGraph<T> subgraph = new AdjacencyListGraph<>();

        // Agrega los vértices especificados al subgrafo.
        for (T vertex : vertices) {

            if (this.contains(vertex)) {
                subgraph.add(vertex);
            }
        }

        // Agrega las aristas del subgrafo original al nuevo subgrafo.
        for (T vertex : subgraph.getAllVertices()) {
            for (T to : getOutwardEdges(vertex)) {
                if (vertices.contains(to)) {
                    subgraph.connect(vertex, to);
                }
            }
        }

        return subgraph;
    }

}