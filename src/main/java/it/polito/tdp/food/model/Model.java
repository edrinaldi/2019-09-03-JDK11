package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<PorzioneConnessa> camminoOttimo;
	private int pesoCammino;
	
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
		System.out.printf("# archi: %d\n", this.grafo.edgeSet().size());
	}
	
	public List<PorzioneConnessa> trovaPorzioniConnesse(String partenza) {
		List<PorzioneConnessa> connessi = new ArrayList<>();
		for(DefaultWeightedEdge e : this.grafo.edgesOf(partenza)) {
			String tipoPorzione = Graphs.getOppositeVertex(this.grafo, e, partenza);
			int peso = (int)this.grafo.getEdgeWeight(e);
			connessi.add(new PorzioneConnessa(tipoPorzione, peso));
		}
		Collections.sort(connessi);
		
		// console
		System.out.print(connessi + "\n");
		
		return connessi;
	}
	
	/*
	 * modifica gli attributi di classe 'camminoOttimo' e 'pesoCammino
	 */
	public void calcolaCammino(int N, String partenza) {
		// inizializzo dati in uscita
		this.camminoOttimo = new ArrayList<>();
		this.pesoCammino = 0;
		
		// avvio la ricorsione
		List<PorzioneConnessa> parziale = new ArrayList<>();
		parziale.add(new PorzioneConnessa(partenza, 0));
		this.ricerca(parziale, N);
	}
	
	/*
	 * il cuore della ricorsione
	 */
	private void ricerca(List<PorzioneConnessa> parziale, int N) {
		if(parziale.size() == N) {
			// soluzione parziale è anche totale
			
			if(this.calcolaPeso(parziale) > this.pesoCammino) {
				
				// aggiorno la soluzione ottima
				this.pesoCammino = this.calcolaPeso(parziale);
				this.camminoOttimo = new ArrayList<>(parziale);
			}
			return;
		}
		PorzioneConnessa ultimoInserito = parziale.get(parziale.size()-1);
		for(DefaultWeightedEdge e : this.grafo.edgesOf(ultimoInserito.getTipoPorzione())) {
			String tipoPorzione = Graphs.getOppositeVertex(this.grafo, e, ultimoInserito.getTipoPorzione());
			int peso = (int)this.grafo.getEdgeWeight(e);
			PorzioneConnessa passo = new PorzioneConnessa(tipoPorzione, peso);
			
			boolean trovato = false;
			for(PorzioneConnessa p : parziale) {
				if(p.getTipoPorzione().compareTo(tipoPorzione) == 0) {
					trovato = true;
				}
			}
			if(trovato == false) {
				parziale.add(passo);
				this.ricerca(parziale, N);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	public List<PorzioneConnessa> getCammino() {
		return this.camminoOttimo;
	}
	
	public int getPesoCammino() {
		return this.pesoCammino;
	}
	
	private int calcolaPeso(List<PorzioneConnessa> parziale) {
		int pesoTot = 0;
		for(PorzioneConnessa p : parziale) {
			pesoTot += p.getPeso();
		}
		return pesoTot;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getVertici() {
		List<String> vertici = new ArrayList<String>(this.grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	
	public boolean isGrafoCreato() {
		return this.grafo!=null;
	}
}
