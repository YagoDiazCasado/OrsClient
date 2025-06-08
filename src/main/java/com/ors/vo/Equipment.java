package com.ors.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Equipment {
	
	@JsonProperty("ownerId")
	private String ownerId;

	@JsonProperty("owner")
	private PJ owner;

	@JsonProperty("casco")
	private Item head;

	@JsonProperty("pecho")
	private Item chest;

	@JsonProperty("piernas")
	private Item legs;

	@JsonProperty("pies")
	private Item feet;

	@JsonProperty("extra_1")
	private Item extra1;

	@JsonProperty("extra_2")
	private Item extra2;

	@JsonProperty("extra_3")
	private Item extra3;

	@JsonIgnore
	private List<Item> equip = new ArrayList<>();

	public Equipment() {}

	public Equipment(PJ owner, Item head, Item chest, Item legs, Item feet) {
		this.owner = owner;
		this.head = head;
		this.chest = chest;
		this.legs = legs;
		this.feet = feet;
		this.extra1 = null;
		this.extra2 = null;
		this.extra3 = null;
	}

	public Equipment(PJ pj) {
		this.owner = pj;
	}

	// Getters y setters
	public PJ getOwner() {
		return owner;
	}

	public void setOwner(PJ owner) {
		this.owner = owner;
	}

	public Item getHead() {
		return head;
	}

	public void setHead(Item head) {
		this.head = head;
	}

	public Item getChest() {
		return chest;
	}

	public void setChest(Item chest) {
		this.chest = chest;
	}

	public Item getLegs() {
		return legs;
	}

	public void setLegs(Item legs) {
		this.legs = legs;
	}

	public Item getFeet() {
		return feet;
	}

	public void setFeet(Item feet) {
		this.feet = feet;
	}

	public Item getExtra1() {
		return extra1;
	}

	public void setExtra1(Item extra1) {
		this.extra1 = extra1;
	}

	public Item getExtra2() {
		return extra2;
	}

	public void setExtra2(Item extra2) {
		this.extra2 = extra2;
	}

	public Item getExtra3() {
		return extra3;
	}

	public void setExtra3(Item extra3) {
		this.extra3 = extra3;
	}

	// Equipamiento actual calculado
	@JsonIgnore
	public List<Item> getEquip() {
		equip.clear();
		if (chest != null) equip.add(chest);
		if (head != null) equip.add(head);
		if (legs != null) equip.add(legs);
		if (feet != null) equip.add(feet);
		if (extra1 != null) equip.add(extra1);
		if (extra2 != null) equip.add(extra2);
		if (extra3 != null) equip.add(extra3);
		return equip;
	}

	public void setEquip(List<Item> equip) {
		this.equip = equip;
	}

	@Override
	public String toString() {
		return "Equipment{" +
				"owner=" + owner +
				", head=" + head +
				", chest=" + chest +
				", legs=" + legs +
				", feet=" + feet +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Equipment other = (Equipment) obj;
		return Objects.equals(owner, other.owner);
	}
}
