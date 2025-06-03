package main.java.com.ors.usables;

import java.text.DecimalFormat;

import main.java.com.ors.services.BodyTypeService;
import main.java.com.ors.services.ComunAlmacen;
import main.java.com.ors.services.ItemService;
import main.java.com.ors.services.PjService;
import main.java.com.ors.utiles.Combo;
import main.java.com.ors.utiles.EnumsDeItems.Distance;
import main.java.com.ors.utiles.Habilidad;
import main.java.com.ors.vo.BodyType;
import main.java.com.ors.vo.PJ;

// NO existe el arma manos, se falsea cuando no hay arma
//df.format(pj.getStr() / (2 * pj.getModS())) es su daño de cada vez 

public class PjUsables {
	private DecimalFormat df = new DecimalFormat("#.##");

	////////////////////////////////////////////////////////////
	///////////////////////////////////////// Metodos de Accion

	public String atacar(PJ pj, int diff, boolean adventage) throws Exception {

		String extra = "";
		double mods = 1.0;

		if (pj.getWeapon().equals(null)) {
			return fistAttack(pj);
		} else {
			if (pj.getWeapon().getDistance().equals(Distance.MELEE)) { // Arma mele
				useActions(-1, pj);
				if (pj.getWeapon().getWeight() > 8) { // Arma pesada
					diff = (int) (diff * pj.getModD());
					pj.setXpS(pj.getXpS() + 2);
				} else { // Arma ligera
					diff = (int) (diff * pj.getModS());
					pj.setXpD(pj.getXpD() + 1);
					pj.setXpA(pj.getXpA() + 1);
					mods = (Math.min(pj.getModD(), pj.getModA()));
				}
			} else { // Arma distancia
				if (pj.getPower().equals("JANO")) {
					diff -= diff / 2;
				}
				diff = (int) (diff * pj.getModM());
			}

			// Entrega
			Combo ataque = ItemService.atacar(pj.getWeapon(), diff, adventage, pj);
			pj = ataque.getPj();
			int basicDamage = ataque.getDano();

			if (basicDamage == 0) {
				return "Fallastes";
			} else if (basicDamage == -1) {
				return "No hay municion";
			} else {
				if (pj.getPower().equals("LEIRZA") && basicDamage >= 1
						&& pj.getWeapon().getDistance().equals(Distance.MELEE)) {
					extra = "\n" + Habilidad.getHabilidadMethod("drenarVida", pj, adventage, basicDamage);
				}
				return "Ejecuta " + df.format((basicDamage / mods)) + " con " + pj.getWeapon().getName() + "" + extra;
			}

		}
	}

	public String fistAttack(PJ pj) throws Exception {
		int entrega = 1;
		int mod = 0;
		switch (pj.getBasicHitter()) {
		case FISTS:
			mod = (int) (pj.getStr() / pj.getModS());
			break;
		case LEGS:
			mod = (int) (pj.getStr() / pj.getModA());
			break;
		case ELBOWS:
			mod = (int) (pj.getStr() / pj.getModS());
			break;
		case KNEES:
			mod = (int) (pj.getStr() / (pj.getModA() - pj.getModS()));
			break;
		case FINGERS:
			mod = (int) (pj.getStr() / pj.getModM());
			break;
		case BLADEHANDS:
			mod = (int) (pj.getStr() / pj.getModA());
			break;
		}
		if (mod <= 1)
			mod = 4;
		entrega = ComunAlmacen.dado.nextInt(1, mod);
		return "Ejecuta " + entrega + " con " + pj.getBasicHitter();

	}

	public String checkDitance(PJ pj, int m) {
		if (pj.getSpeed() < m) {
			int gasto = (int) ((pj.getSpeed() - m) * pj.getModA());
			return "Gastarás " + gasto + " acciones en correr " + m;
		} else {
			return "Puedes correr eso sin usar acciones";
		}
	}

	////////////////////////////////////////////////////////////
	///////////////////////////////////////// Metodos de Usables

	// kcal hp acc. Negativo resta positivo suma
	public void makeEffect(String amountCollection, PJ pj) throws Exception {
		String[] amount = amountCollection.split(" ");
		int kcal = Integer.parseInt(amount[0]);
		int hp = Integer.parseInt(amount[1]);
		int acc = Integer.parseInt(amount[2]);

		if (acc != 0) {
			useActions(acc, pj);
		}
		if (hp != 0) {
			useHp(hp, pj);
		}
		if (kcal != 0) {
			useKcal(kcal, pj);
		}

	}

	public void useActions(int i, PJ pj) throws Exception {
		System.out.println((pj.getActions() + 1) + " acciones");
		if ((pj.getActions() + i) >= 0) {
			pj.setActions(Math.min(pj.getActions() + i, pj.getMaxActions()));
			if (i < 0) {
				if (i > 4) {
					pj.setXpS(pj.getXpS() + 1);
				} else {
					pj.setXpA(pj.getXpA() + 1);
				}
				System.out.println("Entro a useKcal");
				double gasto = (i * pj.getModA() * (40 * pj.getWeight() / (80 * pj.getModS())));
				useKcal(gasto, pj);
			}
		} else {
			throw new Exception("No tienes acciones suficientes");
		}
	}

	public void useHp(int i, PJ pj) throws Exception {
		if (pj.getHp() + i > 0) {
			pj.setHp(Math.min(pj.getHp() + i, pj.getMaxHp()));
			if (i < 0) {
				pj.setXpE(pj.getXpE() + i);
				double gasto = (i * (20 * pj.getWeight() / (100 / pj.getModE())));
				useKcal(gasto, pj);
			}
		} else {
			pj.setHp(0);
			throw new Exception(pj.getName() + " ha muerto");
		}
	}

	public void useKcal(double i, PJ pj) throws Exception {
		System.out.println("Antes: kcal=" + pj.getKcal() + " i=" + i);
		if (pj.getPower().equals("BUNRAKU")) {
			i -= i / 2;
		}
		if (pj.getPower().equals("LEIRZA")) {
			Habilidad.regenerar(pj).aplicar();
		}
		System.out.println(".....................................................");
		pj.setKcal((int) Math.max(0, Math.min(pj.getKcal() + i, pj.getMaxKcal())));
		if (i < 0) {
			if (pj.getKcal() < pj.getMaxKcal() / 6) {
				pj.setXpM(pj.getXpM() + 1);
				pj.setXpE(pj.getXpE() + 1);
				pj.setHp(pj.getHp() - (pj.getHp() / 8));
			} else if (pj.getKcal() <= pj.getMaxKcal() / 2) {
				pj.setXpM(pj.getXpM() + 1);
			}
		}
		System.out.println("Después: kcal=" + pj.getKcal());

	}

	// 1 es descanso corto. DEbe de gastar menos de todo pero mejorar poc ola xp
	// 2 es descanso largo. Debe de gastar más de todo peromejorar mucho la xp
	public void sleep(PJ pj, int condition) throws Exception {
		double modPeso = 0.0;
		double kcalActual = pj.getKcal(); // funcionara porque s double, sino se raya con el int del atrib
		double kcalMax = pj.getMaxKcal();

		if (kcalActual < kcalMax * 0.5) {
			modPeso = -((kcalMax - kcalActual) / 2500.0);
		} else if (kcalActual >= kcalMax * 0.66) {
			modPeso = kcalActual / 3000.0;
		}

		pj.setWeightLose(pj.getWeightLose() + modPeso);
		pj.setXpM((int) (pj.getXpM() + 1 * condition));
		pj.setXpE((int) (pj.getXpE() + 1 * condition));

		// REGEN
		int hpRecuperada = Math.min((int) (pj.getHp() * condition), pj.getMaxHp());
		int accionesRecuperadas = Math.min((int) ((Math.max(pj.getActions(), 2)) * (condition / 2.0)),
				pj.getMaxActions());
		int kcalGastadas = (int) (-pj.getKcal() * (condition / 4.0)); // esto negativo porque es lo unico que se resta

		String envio = kcalGastadas + " " + hpRecuperada + " " + accionesRecuperadas;
		System.err.println(envio);
		makeEffect(envio, pj);
	}

	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////// Dados

	public int diceLooby(PJ pj, String type) { // devuelve el max dado
		new PjService();
		int statLv = PjService.getStat(type, pj);
		if (statLv <= 5) {
			return 2;
		} else if (statLv > 5 && statLv <= 15) {
			return 4;
		} else if (statLv > 15 && statLv <= 25) {
			return 6;
		} else if (statLv > 25 && statLv <= 30) {
			return 8;
		} else if (statLv > 30 && statLv <= 45) {
			return 10;
		} else if (statLv > 45 && statLv <= 55) {
			return 12;
		} else if (statLv > 55 && statLv <= 65) {
			return 16;
		} else if (statLv > 65 && statLv <= 75) {
			return 18;
		} else {
			return 20;
		}
	}

	public void useGlimmers(int numero, PJ selected) {
		try {
			if (numero < 0 && selected.getGlimmers() < numero) {
				selected.setGlimmers(0);
			} else {
				selected.setGlimmers(selected.getGlimmers() + numero);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void agregarBT(PJ tempo, String bb) {
		try {
			System.out.println("/////////////////////1/////////////El nombre del bb es= " + bb);
			BodyType b = BodyTypeService.obtenerPorId(bb);
			tempo.getBodyTypes().add(b);
			if (!tempo.getBodyTypes().contains(b)) {
				PjService.AddBodyType(b, tempo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
