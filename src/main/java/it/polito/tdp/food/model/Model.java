package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		// inizializzo il grafo
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(calorie));
		
		// aggiungo gli archi
		for(Adiacenza a : this.dao.getAdiacenze()) {
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2())) {
				Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
		
		// console
		System.out.printf("# vertici: %d\n", this.grafo.vertexSet().size());
		System.out.printf("# archi: %d", this.grafo.edgeSet().size());
	}
	
	public List<PorzioneConnessa> trovaPorzioniConnesse(String partenza) {
		List<PorzioneConnessa> connessi = new ArrayList<>();
		for(DefaultWeightedEdge e : this.grafo.edgesOf(partenza)) {
			String tipoPorzione = Graphs.getOppositeVertex(this.grafo, e, partenza);
			int peso = (int)this.grafo.getEdgeWeight(e);
			connessi.add(new PorzioneConnessa(tipoPorzione, peso));
		}
		
		// console
		System.out.print(connessi);
		
		return connessi;
	}
}
