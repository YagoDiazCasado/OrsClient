package main.java.com.ors.vo;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private PJ pj;
	private Item item;

	@JsonProperty("cantidad")
	private int quantity;

	@JsonProperty("peso_unitario")
	private double singleWeight;

	@JsonProperty("nombre_objeto")
	private String objectName;

	public Inventory() {
	}

	public Inventory(Item i, PJ p) {
		this.pj = p;
		this.item = i;
	}

	public Inventory(Item i, PJ p, int cant) {
		this.pj = p;
		this.item = i;
		this.quantity = cant;
	}

	public Inventory(Item i, PJ p, int quantity, double singleWeight, String objectName) {
		this.pj = p;
		this.item = i;
		this.quantity = quantity;
		this.singleWeight = singleWeight;
		this.objectName = objectName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSingleWeight() {
		return singleWeight;
	}

	public void setSingleWeight(double singleWeight) {
		this.singleWeight = singleWeight;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return item.getName() ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, pj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return Objects.equals(item, other.item) && Objects.equals(pj, other.pj);
	}
	
	
	
	

}
