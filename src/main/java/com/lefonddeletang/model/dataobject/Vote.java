package com.lefonddeletang.model.dataobject;

/**
 * DataObject représentant un vote pour un Podium
 */
public class Vote {
	/** Tableau d'entiers contenant 3 identifiants d'Item */
	private int[] order;
	
	public int[] getOrder() {
		return this.order;
	}
	public void setOrder(int[] order) {
		this.order = order;
	}
}
