import java.util.ArrayList;
import java.util.List;

//Correr de la siguiente forma java -ea main.java

public class main {
    public static void main(String[] args) {

        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        System.out.println("Inicio de casos de pruebas");

        // add()
        assert graph.add("A");
        assert graph.add("B");
        assert graph.add("C");
        assert graph.add("D");

        // add() vertice ya existente
        assert !graph.add("C");

        // Connect()
        assert graph.connect("A", "B");
        assert graph.connect("A", "C");
        assert graph.connect("B", "C");
        assert graph.connect("B", "D");

        // Connect() conectar asi misma
        assert !graph.connect("B", "B");

        // Connect() Arista ya existente
        assert !graph.connect("B", "C");

        // Connect()

        // Connect() vertices que no existen
        assert !graph.connect("Z", "C");
        assert !graph.connect("B", "X");
        assert !graph.connect("Z", "X");

        // Disconect()
        assert graph.disconnect("B", "D");
        assert !graph.disconnect("B", "D");
        assert !graph.disconnect("D", "D");

        // Disconect() vertices que no existen
        assert !graph.disconnect("Z", "C");
        assert !graph.disconnect("B", "X");
        assert !graph.disconnect("Z", "X");

        // Contains()
        assert graph.contains("A");
        assert !graph.contains("Z");

        // getInwardEdges()
        List<String> inwardEdges = graph.getInwardEdges("C");
        assert inwardEdges.contains("A");
        assert inwardEdges.contains("B");
        assert !inwardEdges.contains("D");

        assert null == graph.getInwardEdges("Z");

        // getOutwardEdges()
        List<String> outwardEdges = graph.getOutwardEdges("A");
        assert outwardEdges.contains("B");
        assert outwardEdges.contains("C");
        assert !outwardEdges.contains("D");

        assert null == graph.getOutwardEdges("Z");

        // getVerticesConnectedTo()
        List<String> connectedVertices = graph.getVerticesConnectedTo("A");
        assert connectedVertices.contains("B");
        assert connectedVertices.contains("C");

        assert null == graph.getVerticesConnectedTo("Z");

        // remove()
        assert graph.remove("D");
        assert !graph.contains("D");
        assert !graph.remove("D");

        // size()
        assert graph.size() == 3;

        // subgraph()
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("C");

        AdjacencyListGraph<String> subgraph = graph.subgraph(list);
        assert subgraph.contains("A");
        assert subgraph.contains("C");
        assert !subgraph.contains("B");

        // getVertices()
        List<String> vertices = graph.getAllVertices();
        assert vertices.contains("A");
        assert vertices.contains("B");
        assert vertices.contains("C");

        System.out.println("Todos los casos funcionan");

    }
}
