package main.java.com.ors.vo;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonUnwrapped
	private InventoryId idIn;

	@JsonProperty("cantidad")
	private int quantity;

	@JsonProperty("peso_unitario")
	private double singleWeight;

	@JsonProperty("nombre_objeto")
	private String objectName;

	public Inventory() {
	}

	public Inventory(Item i, PJ p) {
		this.idIn = new InventoryId(p, i);
	}

	public Inventory(InventoryId idIn, int quantity, double singleWeight, String objectName) {
		this.idIn = idIn;
		this.quantity = quantity;
		this.singleWeight = singleWeight;
		this.objectName = objectName;
	}

	public Inventory(Item i, PJ p, int quantity, double singleWeight, String objectName) {
		this.idIn = new InventoryId(p, i);
		this.quantity = quantity;
		this.singleWeight = singleWeight;
		this.objectName = objectName;
	}

	// Getters y setters
	public InventoryId getId() {
		return idIn;
	}

	public void setId(InventoryId id) {
		this.idIn = id;
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

	public PJ getPj() {
		return idIn != null ? idIn.getPj() : null;
	}

	public void setPj(PJ pj) {
		if (idIn == null)
			idIn = new InventoryId();
		idIn.setPj(pj);
	}

	public static class InventoryId implements Serializable {

		private static final long serialVersionUID = 1L;
		private PJ pj;
		private Item item;

		public InventoryId() {
		}

		public InventoryId(PJ pj, Item item) {
			this.pj = pj;
			this.item = item;
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

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof InventoryId))
				return false;
			InventoryId that = (InventoryId) o;
			return Objects.equals(pj, that.pj) && Objects.equals(item, that.item);
		}

		@Override
		public int hashCode() {
			return Objects.hash(pj, item);
		}
	}
}
