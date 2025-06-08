package com.ors.vo;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ors.utiles.EnumsDeItems.DamageType;
import com.ors.utiles.EnumsDeItems.Distance;
import com.ors.utiles.EnumsDeItems.ItemFamily;
import com.ors.utiles.EnumsDeItems.ItemShape;
import com.ors.utiles.EnumsDeItems.Rarity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id_O;

	@JsonProperty("nombre")
	private String name;

	private Rarity rarity;
	private String modEquipo;
	private ItemFamily itemFamily;
	private ItemShape itemShape;
	private double value;
	private double weight;
	private int basicDamage;
	private DamageType damageType;
	private Distance distance;
	private String consumEffect;
	private String descripcion;
	private String imagenUrl;

	public Item() {
	}

	// Constructor Total
	public Item(String name, Rarity rarity, String modEquipo, ItemFamily itemFamily, ItemShape itemShape, double value,
			double weight, int basicDamage, DamageType damageType, Distance distance, String consumEffect,
			String descripcion) {
		this.name = name;
		this.rarity = rarity;
		this.modEquipo = modEquipo;
		this.itemFamily = itemFamily;
		this.itemShape = itemShape;
		this.value = value;
		this.weight = weight;
		this.basicDamage = basicDamage;
		this.damageType = damageType;
		this.distance = distance;
		this.consumEffect = consumEffect;
		this.descripcion = descripcion;
	}

	// Otros constructores útiles
	public Item(String name, Rarity rarity, ItemFamily itemFamily, ItemShape itemShape, double value, double weight,
			int basicDamage, DamageType damageType, Distance distance, String descripcion) {
		this(name, rarity, null, itemFamily, itemShape, value, weight, basicDamage, damageType, distance, null,
				descripcion);
	}

	public Item(String name, Rarity rarity, String modEquipo, ItemFamily itemFamily, ItemShape itemShape, double value,
			double weight, String descripcion) {
		this(name, rarity, modEquipo, itemFamily, itemShape, value, weight, 0, null, null, null, descripcion);
	}

	public Item(String name, Rarity rarity, ItemFamily itemFamily, ItemShape itemShape, double value, double weight,
			String consumEffect, String descripcion) {
		this(name, rarity, null, itemFamily, itemShape, value, weight, 0, null, null, consumEffect, descripcion);
	}

	public Item(String name, Rarity rarity, ItemFamily itemFamily, ItemShape itemShape, double value, double weight,
			String descripcion) {
		this(name, rarity, null, itemFamily, itemShape, value, weight, 0, null, null, null, descripcion);
	}

	// Métodos auxiliares
	public String showInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n========== ITEM INFO ==========\n");
		sb.append("🔹 Nombre         : ").append(name).append("\n");
		sb.append("🔹 Rareza         : ").append(rarity).append("\n");
		sb.append("🔹 Familia        : ").append(itemFamily).append("\n");
		sb.append("🔹 Forma          : ").append(itemShape != null ? itemShape : "N/A").append("\n");
		sb.append("🔹 Valor          : ").append(value).append("\n");
		sb.append("🔹 Peso           : ").append(weight).append("\n");

		if (itemFamily == ItemFamily.ITEM) {
			// nada
		} else if (itemFamily == ItemFamily.EDIBLE) {
			sb.append("\n------ USO ------\n");
			sb.append("🔸 Efecto Consum  : ").append(consumEffect != null ? consumEffect : "Ninguno").append("\n");
		} else {
			if (itemFamily != ItemFamily.EQUIPMENT) {
				sb.append("\n------ COMBATE ------\n");
				sb.append("🔸 Daño Base      : ").append(basicDamage).append("\n");
				sb.append("🔸 Tipo de Daño   : ").append(damageType != null ? damageType : "N/A").append("\n");
				sb.append("🔸 Distancia      : ").append(distance != null ? distance : "N/A").append("\n");
			}
			sb.append("\n------ MODIFICADOR ------\n");
			sb.append("🔸 Mod     : ").append(modEquipo).append("\n");
		}

		sb.append("\n------ DESCRIPCIÓN ------\n");
		sb.append(descripcion != null ? descripcion : "Sin descripción").append("\n");
		sb.append("================================\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Item [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_O);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(id_O, other.id_O);
	}

	// Getters y Setters
	public Long getId_O() {
		return id_O;
	}

	public void setId_O(Long id_O) {
		this.id_O = id_O;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public String getModEquipo() {
		return modEquipo;
	}

	public void setModEquipo(String modEquipo) {
		this.modEquipo = modEquipo;
	}

	public ItemFamily getItemFamily() {
		return itemFamily;
	}

	public void setItemFamily(ItemFamily itemFamily) {
		this.itemFamily = itemFamily;
	}

	public ItemShape getItemShape() {
		return itemShape;
	}

	public void setItemShape(ItemShape itemShape) {
		this.itemShape = itemShape;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getBasicDamage() {
		return basicDamage;
	}

	public void setBasicDamage(int basicDamage) {
		this.basicDamage = basicDamage;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	public String getConsumEffect() {
		return consumEffect;
	}

	public void setConsumEffect(String consumEffect) {
		this.consumEffect = consumEffect;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
