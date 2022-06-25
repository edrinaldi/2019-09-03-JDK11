package it.polito.tdp.food.model;

public class TestModel {
	public static void main(String[] args) {
		Model m = new Model();
		m.creaGrafo(100);
		m.trovaPorzioniConnesse("can");
		m.calcolaCammino(3, "can");
		System.out.println("Trovato cammino:");
		for(PorzioneConnessa p : m.getCammino()) {
			System.out.println("- " + p.getTipoPorzione());
		}
		System.out.println("Peso totale = " + m.getPesoCammino());
	}
}
