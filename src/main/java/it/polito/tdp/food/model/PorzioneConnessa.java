package it.polito.tdp.food.model;

public class PorzioneConnessa implements Comparable<PorzioneConnessa>{
	private String tipoPorzione;
	private int peso;
	public PorzioneConnessa(String tipoPorzione, int peso) {
		super();
		this.tipoPorzione = tipoPorzione;
		this.peso = peso;
	}
	public String getTipoPorzione() {
		return tipoPorzione;
	}
	public void setTipoPorzione(String tipoPorzione) {
		this.tipoPorzione = tipoPorzione;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return tipoPorzione + ", " + peso;
	}
	@Override
	public int compareTo(PorzioneConnessa o) {
		// TODO Auto-generated method stub
		return this.tipoPorzione.compareTo(o.tipoPorzione);
	}
	
}
