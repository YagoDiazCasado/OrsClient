package com.ors.utiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorAleatorios {
	//hola

	public static Random dado = new Random();

	public static String generarNombre(String raza) {
		StringBuilder entrega = new StringBuilder();
		int longi = dado.nextInt(1, 2);

		entrega.append(genPrefijo(raza));
		for (int i = 0; i < longi; i++) {
			entrega.append(sufijo(raza));
		}
		return entrega.toString();
	}

	private static String sufijo(String raza) {
		List<String> sufijos = new ArrayList<String>();
		switch (raza) {
		case "SOLEO":
			sufijos.addAll(List.of("or", "ek", "en", "os", "ok", "ion", "ar", "iel", "arion", "arok", "osir", "iok",
					"eil", "ior", "oros", "akan", "ionor", "alon", "eth"));
			break;

		case "TECHITA":
			sufijos.addAll(List.of("ix", "ok", "ron", "tek", "ox", "ak", "bit", "zix", "nek", "rix", "lok", "trax",
					"nak", "krax", "zin", "mek", "tik", "bot", "chok"));
			break;

		case "HUMANO":
			sufijos.addAll(List.of("el", "ára", "és", "hian", "tar", "sen", "kov", "rov", "hál", "eth", "'ho", "zhen",
					"mir", "éor", "rah", "hán", "lor", "vek", "hol", "von", "'his", "mel", "da", "ly", "ump"));
			break;

		case "CONSCIENTE":
			sufijos.addAll(List.of("ess", "i", "ae", "um", "eth", "or", "iel", "uris", "oth", "em", "oas", "uro",
					"isel", "ilum", "ithar", "alis", "etha", "ionis", "ethel"));
			break;

		case "ANIMAL":
			sufijos.addAll(List.of("fang", "tail", "claw", "hoof", "bark", "seed", "mane", "fur", "paw", "tooth",
					"whisker", "thorn", "leaf", "pelt", "root", "whistle", "hide", "howl", "growl", "burrow"));
			break;

		case "TRANSPORTE":
			sufijos.addAll(List.of(" de carga", " de guerra", " de correos", " del alquimista", " de escolta",
					" funerario", " real", " del gremio", " nómada", " de exploración", " ceremonial",
					" de transporte rúnico", " imperial", " de suministros mágicos", " de los errantes", " del consejo",
					" blindado", " aéreo", " anfibio", " desmontable"));
			break;

		default:
			sufijos.add("x");
			break;
		}
		return sufijos.get(dado.nextInt(sufijos.size()));
	}

	private static String genPrefijo(String raza) {
		List<String> prefs = new ArrayList<String>();
		switch (raza) {
		case "SOLEO":
			prefs.addAll(List.of("Kó", "Ro", "Ka", "Sol", "Lio", "Or", "Aurel", "Elion", "Sar", "Valo", "Il", "Os",
					"Nok", "Ar", "Lor", "Thion", "Yel", "Orek", "Kiro"));
			break;

		case "TECHITA":
			prefs.addAll(List.of("Tek", "Kra", "Kro", "Kym", "Bit", "Kod", "Zin", "Krex", "Mod", "Trok", "Gyn", "Xel",
					"Trak", "Kib", "Spro", "Nyk", "Dyz", "Pix", "Brot", "Raj", "Kum", "Akh", "Bhav", "Zah", "Kirov",
					"Taj", "Nad", "Umir", "Jal", "Azan", "Kahi", "Tarek", "Musa", "Ziki", "Abel", "Bako", "Yaro",
					"Jato", "M'kai", "Ombo", "Nuru", "Tebo", "Chika"));
			break;

		case "HUMANO":
			prefs.addAll(List.of("Hál", "Hal", "Lák", "Lak", "Lét", "Let", "Sh", "Jó", "Jo", "Áz", "Az", "Hó", "Ho",
					"Doré", "Dore", "Thár", "Thar", "Ísk", "Isk", "Míh", "Mih", "Zál", "Zal", "Édh", "Edh", "Lór",
					"Lor", "Rénh", "Renh", "Álh", "Alh", "Vól", "Vol", "Céh", "Ceh", "Néh", "Neh", "Hál", "Hal", "Lák",
					"Lak", "Lét", "Let", "Sh", "Jó", "Jo", "Áz", "Az", "Hó", "Ho", "Doré", "Dore", "Thár", "Thar",
					"Ísk", "Isk", "Míh", "Mih", "Zál", "Zal", "Édh", "Edh", "Lór", "Lor", "Rénh", "Renh", "Álh", "Alh",
					"Vól", "Vol", "Céh", "Ceh", "Néh", "Neh", "Vlad", "Nikol", "Sergei", "Pyotr", "Anton", "Grigor",
					"Maks", "Zhen", "Borya", "Dmitr", "Rask", "Oleg", "Ravi", "Amir", "Zahir", "Shad", "Khal", "Imran",
					"Yasir", "Tanvir", "Rajan", "Ishar", "Javed", "Ashan", "Samir", "Fayez", "Omran"));
			break;

		case "CONSCIENTE":
			prefs.addAll(List.of("Áe", "Ae", "Ély", "Ely", "Ómn", "Omn", "Quá", "Qua", "Íth", "Ith", "Ál", "Al", "Úl",
					"Ul", "Tháe", "Thae", "Éris", "Eris", "Órun", "Orun", "Ísil", "Isil", "Noá", "Noa", "Uréth",
					"Ureth", "Ásir", "Asir", "Ílun", "Ilun", "Thién", "Thien", "Éreth", "Ereth", "Únar", "Unar", "Ósi",
					"Osi"));
			break;

		case "ANIMAL":
			prefs.addAll(List.of("Lúp", "Lup", "Fóx", "Fox", "Wól", "Wol", "Cár", "Car", "Háwk", "Hawk", "Béa", "Bea",
					"Bád", "Bad", "Sérp", "Serp", "Stág", "Stag", "Ótter", "Otter", "Crów", "Crow", "Hóund", "Hound",
					"Móle", "Mole", "Ráv", "Rav", "Háre", "Hare", "Béar", "Bear", "Fáwn", "Fawn", "Éel", "Eel", "Bóar",
					"Boar", "Lárk", "Lark"));
			break;

		case "TRANSPORTE":
			prefs.addAll(List.of("Carromato", "Diligencia", "Carruaje", "Trineo", "Galera", "Carreta", "Calesa",
					"Balsa", "Trasto", "Furgón", "Carro", "Nave de lino", "Carro estandarte", "Cúpula rodante",
					"Plataforma mágica", "Góndola solar", "Torre rodante", "Yunta", "Carro arcano"));
			break;

		default:
			prefs.add("X");
			break;
		}
		return prefs.get(dado.nextInt(prefs.size()));
	}

}
