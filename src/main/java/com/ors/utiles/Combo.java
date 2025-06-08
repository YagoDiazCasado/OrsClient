package com.ors.utiles;

import com.ors.vo.Item;
import com.ors.vo.PJ;

public class Combo {
	public int dificultad;
	public boolean ventaja;
	private PJ pj;
	private int dano;
	public Item item;

	public Combo(PJ pj, int dano, int diff, boolean vent, Item it) {
		this.pj = pj;
		this.dano = dano;
		this.dificultad = diff;
		this.ventaja = vent;
		this.item = it;
	}

	public Combo(PJ pj, int envio) {
		this.pj = pj;
		this.dano = envio;
	}
	public Combo() {
    }
	public PJ getPj() {
		return pj;
	}

	public void setPj(PJ pj) {
		this.pj = pj;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	public boolean isVentaja() {
		return ventaja;
	}

	public void setVentaja(boolean ventaja) {
		this.ventaja = ventaja;
	}
}
