package com.ors.utiles;

import java.util.ArrayList;
import java.util.Arrays;

public class LibreriaDeListas {

	public ArrayList<String> body_Types = new ArrayList<String>(
			Arrays.asList("strong", "athletic", "tank", "geek", "skilled", "delusional"));
	public ArrayList<String> races = new ArrayList<String>(Arrays.asList("SOLEO", "HUMANO", "TECHITA"));
	public ArrayList<String> damage_Types = new ArrayList<String>(
			Arrays.asList("explosive", "stab", "cut", "hit", "termal"));
	public ArrayList<String> rarity = new ArrayList<String>(Arrays.asList("SS", "S", "A", "B", "C", "D", "E", "F"));
	public ArrayList<String> item_Types = new ArrayList<String>(
			Arrays.asList("Item", "Consumible", "RangedWeapon", "MeleWeapon"));

	// '0, -25, 30' sería el efecto de una pocion. 0 kcal, resta 25 de vida y suma
	// 30 acciones. Lo leemos haciendo split()
}
