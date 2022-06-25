package it.polito.tdp.food.model;

public class TestModel {
	public static void main(String[] args) {
		Model m = new Model();
		m.creaGrafo(100);
		m.trovaPorzioniConnesse("cherry");
		m.calcolaCammino(3, "cherry");
		System.out.println("Trovato cammino:");
		for(PorzioneConnessa p : m.getCammino()) {
			System.out.println("- " + p.getTipoPorzione());
		}
		System.out.println("Peso totale = " + m.getPesoCammino());
	}
}
