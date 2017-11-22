package ch9;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author pfjia
 * @since 2017/11/21 20:56
 */
public class HelloJGraphT {

	/**
	 * creates a toy directed graph based on URL objects that represents link structure.
	 * @return a graph based on URL objects
	 */
	private static Graph<URL, DefaultEdge> createHrefGraph() {
		Graph<URL, DefaultEdge> graph = new DefaultDirectedGraph<URL, DefaultEdge>(
				DefaultEdge.class);
		try {
			URL amazon = new URL("http://www.amazon.com");
			URL yahoo = new URL("http://www.yahoo.com");
			URL ebay = new URL("http://www.ebay.com");

			// add the vertices
			graph.addVertex(amazon);
			graph.addVertex(yahoo);
			graph.addVertex(ebay);

			// add edges to create linking structure
			graph.addEdge(yahoo, amazon);
			graph.addEdge(yahoo, ebay);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return graph;
	}


	/**
	 * create a toy graph based on String object.
	 * @return a graph based on String object.
	 */
	private static Graph<String, DefaultEdge> createStringGraph() {
		Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";

		// add the vertices
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addVertex(v4);

		// add edges to create a circuit
		graph.addEdge(v2, v3);
		graph.addEdge(v3, v4);
		graph.addEdge(v4, v1);
		return graph;
	}


	public static void main(String[] args) {
		Graph<String, DefaultEdge> stringGraph = createStringGraph();
		System.out.println(stringGraph);

		Graph<URL, DefaultEdge> hrefGraph = createHrefGraph();
		System.out.println(hrefGraph);
	}
}
