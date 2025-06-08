package com.ors.vo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Adventure {

	@JsonProperty("name")
	private String adventureName;

	private String pasword;

	public Adventure() {
	}

	public Adventure(String nom, String p) {
		this.adventureName = nom;
		this.pasword = p;
	}

	public String getAdventureName() {
		return adventureName;
	}

	public void setAdventureName(String name) {
		this.adventureName = name;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	@Override
	public String toString() {
		return adventureName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adventureName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Adventure other = (Adventure) obj;
		return Objects.equals(adventureName, other.adventureName);
	}
	
}
