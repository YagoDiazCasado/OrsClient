package main.java.com.ors.utiles;

import java.util.Random;

import main.java.com.ors.services.ComunAlmacen;
import main.java.com.ors.usables.PjUsables;
import main.java.com.ors.utiles.EnumsDeItems.ItemShape;
import main.java.com.ors.vo.PJ;

public interface Habilidad {

	public PjUsables usable = ComunAlmacen.pU;
	public Random dado = new Random();

	String aplicar(); // String porque siempre devuelve texto, Vamos a intentar que siempre siga un
						// orden el texto:

	static String getHabilidadMethod(String skillName, PJ pj, boolean adv, int ataque) {
		switch (skillName) {
/////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////// GENERALES
		case "combo":
			return Habilidad.combo(pj).aplicar();
		case "ojoCritico":
			return Habilidad.ojoCritico(pj).aplicar();
		case "ganarEspalda":
			return Habilidad.ganarEspalda(pj).aplicar();
		case "protegerEspalda":
			return Habilidad.protegerEspalda(pj).aplicar();
		case "reencoroso":
			return Habilidad.reencoroso(pj).aplicar();
		case "sigiloso":
			return Habilidad.sigiloso(pj).aplicar();
		case "guardiaLejana":
			return Habilidad.guardiaLejana(pj).aplicar();
		case "concentracion":
			return Habilidad.concentracion(pj).aplicar();
		case "placaje":
			return Habilidad.placaje(pj).aplicar();
		case "escabullirte":
			return Habilidad.escabullirte(pj).aplicar();
		case "primeraLente":
			return Habilidad.primeraLente(pj).aplicar();
		case "segundaLente":
			return Habilidad.segundaLente(pj).aplicar();
		case "bloqueo":
			return Habilidad.bloqueo(pj).aplicar();
		case "esquivar":
			return Habilidad.esquivar(pj).aplicar();
		case "parry":
			return Habilidad.parry(pj).aplicar();
/////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////// VASTE
//		case "reflejoVaste":
//			return Habilidad.reflejoVaste(pj).aplicar(); // refleja el daño a quien atacó, pero tambien se lo hace a si
//															// mismo
//		case "transmisionVaste": // le cede el daño recibido a algo que toque y este vivo
//			return Habilidad.transmisionVaste(pj).aplicar();
		case "emisionVaste":
			return Habilidad.emisionVaste(pj).aplicar();
		case "gritoDeVaste":
			return Habilidad.gritoDeVaste(pj).aplicar();
		case "imanVaste":
			return Habilidad.imanVaste(pj).aplicar();
		case "disparoVaste":
			return Habilidad.disparoVaste(pj, adv).aplicar();
		case "reconVaste":
			return Habilidad.reconVaste(pj).aplicar();
		case "corazaDeVaste":
			return Habilidad.corazaDeVaste(pj).aplicar();
		case "corazaTotalDeVaste":
			return Habilidad.corazaTotalDeVaste(pj).aplicar();
		case "golpeVaste":
			return Habilidad.golpeVaste(pj, adv).aplicar();
/////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////// SOLEAS
		case "espirituGuerrero":
			return Habilidad.espirituGuerrero(pj).aplicar();
		case "espirituSuperior":
			return Habilidad.espirituSuperior(pj).aplicar();
		case "espirituGeneroso":
			return Habilidad.espirituGeneroso(pj).aplicar();
		case "gritosIgneos":
			return Habilidad.gritosIgneos(pj).aplicar();
		case "comandarHermanos":
			return Habilidad.comandarHermanos(pj).aplicar();
		case "arenaYCal":
			return Habilidad.arenaYCal(pj).aplicar();
/////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////// JANO
		case "parieJoint":
			return Habilidad.parieJoint(pj).aplicar();
		case "polarisVex":
			return Habilidad.polarisVex(pj).aplicar();
/////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////// BUNRAKU
		case "heigerInvert":
			return Habilidad.heigerInvert(pj).aplicar();
		case "hastroUpter":
			return Habilidad.hastroUpter(pj).aplicar();
/////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////// LEIRZA
		case "drenarVida":
			return Habilidad.drenarVida(pj, adv, ataque).aplicar();
		case "regenerar":
			return Habilidad.regenerar(pj).aplicar();

		default:
			throw new IllegalArgumentException("Habilidad no encontrada: " + skillName);
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////// GENERALES

	static Habilidad bloqueo(PJ pj) {
		if (pj.getActions() >= 2) {
			try {
				usable.useActions(-2, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (dado.nextInt(1, pj.getPreception()) > 10) {
				return () -> "Puede sbloquear el ataque si es inferior a " + (dado.nextInt(1, (int) (pj.getStr())));
			}
		}
		return null;
	}

	static Habilidad esquivar(PJ pj) {
		if (pj.getActions() >= 1) {
			try {
				usable.useActions(-1, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (dado.nextInt(1, pj.getPreception()) > 12) {
				return () -> "Puede esquivar el ataque si es inferior a "
						+ (dado.nextInt(1, (int) (pj.getAcrobatics())));
			}
		}
		return null;
	}

	static Habilidad parry(PJ pj) {
		if (pj.getActions() >= 3) {
			try {
				usable.useActions(-3, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (dado.nextInt(1, pj.getPreception()) > 15) {
				return () -> "Puede ignorar el ataque si es inferior a " + (dado.nextInt(1, (int) (pj.getDex())))
						+ " Y actuar un turno extra ahora mismo";
			}
		}
		return null;
	}

	static Habilidad escabullirte(PJ pj) {
		if (pj.getActions() >= 2) {
			try {
				usable.useActions(-2, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> "Puedes moverte sin recibir ataques de oportunidad durante este turno. Consume Accion de turno";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad sigiloso(PJ pj) {
		return () -> "PASIVA: En sigilo, puede pasar cerca de las personas sin necesidad de tirar dados. Sólo terminará su sigilo si lo ven.";
	}

	static Habilidad reencoroso(PJ pj) {
		return () -> "PASIVA: Después de recibir un ataque, ataca con ventaja si ataca a su anterior agresor";
	}

	static Habilidad concentracion(PJ pj) {
		if (pj.getActions() >= 2) {
			try {
				usable.useActions(-2, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> "Puedes usar esta Habilidad sin consumir turno, poero dentro del tuyo. LA siguiente tirada que hagas tendrá ventaja";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad guardiaLejana(PJ pj) {
		if (pj.getActions() >= 3) {
			try {
				usable.useActions(-3, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> "Gastas El turno en proteger un área 3x3 seleccionada o una persona. Si se mueve, recibes un turno gratis para lo que te plazca, recibirás el tuyo con normalidad cuando llegue";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad protegerEspalda(PJ pj) {
		if (pj.getActions() >= 2) {
			try {
				usable.useActions(-2, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> "Puedes usar esta Habilidad sin consumir turno, poero dentro del tuyo. Evita que puedan tomar tu espalda. Reaccionas como lo harías normalmente";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad ganarEspalda(PJ pj) {
		if (pj.getActions() >= 2) {
			try {
				usable.useActions(-2, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> "Puedes usar esta Habilidad sin consumir turno, pero dentro del tuyo. Permite tomar la espalda de un Personaje seleccionado";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad combo(PJ pj) {
		String envio = "Comienza el combo---------------------";
		double total = 0;
		try {
			if (pj.getActions() >= 2) {
				while (true) {
					int continuidadDiff = dado.nextInt(1, 16);
					String temp = usable.atacar(pj, continuidadDiff, false);
					envio += temp + "\n";
					total += Double.parseDouble(temp.split(" ")[1]);
				}
			}
		} catch (Exception e) {
			envio += "-----------Termina el combo-------------------------" + total + " de daño total --------------";
			final String t = envio;
			return () -> t;
		}
		return () -> "No hay acciones suficientes";
	}

	static Habilidad placaje(PJ pj) {
		if (pj.getActions() >= 2 * pj.getModS()) {
			double dano = 0.0;
			try {
				dano = (pj.getWeight() - pj.getSpeed()) / pj.getModS();
				usable.useActions((int) (-2 * pj.getModS()), pj);
				usable.useHp((int) -dano / 4, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			final double entrega = dano;
			return () -> "Placa con sus " + pj.getWeight() + " kilos y ejerce " + entrega
					+ "  puntos de daño al rival y " + (entrega / 4) + " a si mismo. Empuja " + dado.nextInt(1, 5)
					+ " m";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad ojoCritico(PJ pj) {
		if (pj.getActions() >= 2) {
			try {
				usable.useActions(-2, pj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> "Los próximos 3 turnos tienes ventaja (stackeable) en todas tus tiradas";
		}
		return () -> "Faltan acciones ";
	}

	static Habilidad primeraLente(PJ b) {
		if (b.getWeapon().getItemShape().equals(ItemShape.TELESCOPE)) {
			String dano = "";
			try {
				dano = usable.atacar(b, 10, false); // Poner percepción en su lugar
			} catch (Exception e) {
				e.printStackTrace();
			}
			final String entrega = dano;
			return () -> "Dispara con la lente lejana y ejerce " + entrega
					+ "  en línea fina. Quema al objetivo con -4 durante 3 turnos";
		} else {
			return () -> "Debes equipar un telescopio Astur primero";
		}
	}

	static Habilidad segundaLente(PJ b) {
		if (b.getWeapon().getItemShape().equals(ItemShape.TELESCOPE)) {
			String dano = "";
			try {
				dano = "" + (usable.atacar(b, 3, true));
				usable.useHp(-4, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			final String entrega = dano;
			return () -> "Dispara con la lente cercana y ejerce " + entrega
					+ "  en 6 casillas (2X3). Quema al objetivo con -4 durante 3 turnos";
		} else {
			return () -> "Debes equipar un telescopio Astur primero";
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////// SOLEOS

	static Habilidad espirituGuerrero(PJ b) {
		if (b.getActions() >= 3) {
			try {
				usable.useActions(3, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Todos te atacarán a tí hasta que tu turno vuelva");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad espirituSuperior(PJ b) {
		if (b.getActions() >= 4) {
			try {
				usable.useActions(4, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Todos te atacarán a tí hasta que tu turno vuelva. El primero de los ataques no te afectará");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad espirituGeneroso(PJ b) {
		if (b.getActions() >= 3) {
			try {
				usable.useActions(3, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Permites que otro soleo/techita de tu grupo use tus acciones durante el siguiente turno");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad gritosIgneos(PJ b) {
		if (b.getActions() >= 2) {
			try {
				usable.useActions(2, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Los que lo escuchen atacarán en el siguiente turno al enemigo más cercano.");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad comandarHermanos(PJ b) {
		if (b.getActions() >= (b.getActions() / 5) + 3) {
			try {
				usable.useActions((b.getActions() / 5) + 3, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Puedes dar ordenes a un cualquier Soleo \nQue tenga menos de la mitad de la vida que tú.");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad ojosHermanos(PJ b) {
		if (b.getActions() >= 4) {
			try {
				usable.useActions(4, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Durante 3 turnos Puedes ver desde los ojos de cualquier compañero, incluso sentir lo que siente.");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad alientoFuego(PJ b) {
		if (b.getActions() >= (b.getActions() / 5) + 3) {
			try {
				usable.useActions((b.getActions() / 5) + 3, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Debe de haber una fuente de fuego cercana. Tu aliento puede proyectar el fuego como un chorro a presión:  "
					+ ((b.getVaste() / 4) * 2)
					+ " de poder que se siente como un azote de calor. Puede tener varios efectos de propagación y alcanza "
					+ "30m en cono de incremento 1 cada 2");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad arenaYCal(PJ b) {
		try {
			usable.useActions(b.getMaxActions(), b); // regenera todo
			usable.useHp(-b.getHp() / 3, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> ("Tus Acciones vuelven a su máxima capacidad a cambio de tu sangre..."
				+ "-Un corazón en calor no puede morir-" + "-...Mucho menos rendirse...-"
				+ "Gastas 1 tercio de tu vida actual a cambio de acciones.");
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////// VASTE

	static Habilidad emisionVaste(PJ b) {
		try {
			usable.useActions(-b.getActions() / 4, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> ("Azota " + b.getVaste_Distance() + "m con un poder de " + (b.getVaste()/2));
	}

	static Habilidad gritoDeVaste(PJ b) {
		if (b.getActions() >= 1) {
			try {
				usable.useActions(-1, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("Utilizas tu mente para atacar a distancia y hacer " + usable.diceLooby(b, "min") + " a "
					+ b.getVaste_Distance() + " m");
		} else {
			return () -> ("No hay acciones suficientes");
		}
	}

	static Habilidad imanVaste(PJ b) {
		try {
			usable.useActions(-b.getMaxActions() / 4, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> ("Permite imantar el nombre a cualquier cosa, es decir, tu ser al completo se pega a algo con una fuerza de "
				+ b.getVaste()
				+ "kg. La imantación puede arrastrar a la persona o en su defecto al más ligero de ambos elementos a "
				+ b.getVaste_Distance() + "m");
	}

	static Habilidad disparoVaste(PJ b, boolean adv) {
		try {
			usable.useActions(-b.getActions() / 2, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int a = dado.nextInt(1, 21);
		int c = dado.nextInt(1, 21);
		if (adv && (a >= 10 * b.getModM() || c >= 10 * b.getModM())) {
			return () -> ("Disparas a " + b.getVaste_Distance() + "m en línea recta y Ejerces " + (b.getVaste() / 2)
					+ " puntos de daño");
		} else if (!adv && a >= 10 * b.getModM()) {
			return () -> ("Disparas a " + b.getVaste_Distance() + "m en línea recta Ejerces " + (b.getVaste() / 2)
					+ " puntos de daño");
		} else {
			return () -> ("Fallaste el disparo por sacar " + a + " en la dificultad");
		}
	}

	static Habilidad golpeVaste(PJ b, boolean adv) {
		try {
			int vaste = (b.getVaste());
			int ataque = Integer.parseInt(usable.atacar(b, 1, adv).split(" ")[1]);
			usable.useActions(-b.getActions() / 4, b);
			return () -> (" El ataque se imbuye de vaste y ejerce " + vaste + "(v) + " + ataque + "(a): "
					+ (vaste + ataque) + " puntos de daño.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> "Algo falló";
	}

	static Habilidad reconVaste(PJ b) {
		try {
			usable.useActions(-b.getMaxActions() / 5, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> (b.getVaste_Distance() + "m de rango estudiados");

	}

	static Habilidad corazaDeVaste(PJ b) {
		try {
			usable.useActions(-b.getMaxActions() / 4, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> ("El siguiente ataque recibido quitará " + b.getVaste() / 2 + " puntos menos de daño");

	}

	static Habilidad corazaTotalDeVaste(PJ b) {
		if (b.getActions() >= 6) {
			try {
				usable.useActions(-b.getMaxActions() / 2, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return () -> ("El siguiente ataque que recibas será anulado si se supera dificultad, de lo contrario, quitará sólo la mitad");
		} else {
			return () -> ("No hay acciones");
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////// JANO

	static Habilidad parieJoint(PJ b) {
		try {
			usable.useActions(b.getActions() / 5, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> "Puedes llevar hasta " + b.getMin() / 8 + " persona/as a una distancia de " + b.getVaste_Distance()
				+ "m. No necesitas poder verlos, sólo que acepten mentalmente tu invitación.";

	}

	static Habilidad polarisVex(PJ b) {
		try {
			usable.useActions(b.getActions() / 5, b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return () -> "Puedes secuestrar una zona inhabitada de la realidad por un instante en la dimensión espejo. "
				+ "Al siguiente turno, esa celda recuperará su contenido y lo hará con una onda de choque a su alrededor de "
				+ b.getVaste_Distance() + "m de alcance y " + b.getVaste() / 10 + " de daño.";

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////// BUNRAKU

	static Habilidad heigerInvert(PJ b) {
		try {
			usable.useActions(b.getActions() / 4, b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return () -> "Inviertes la temperatura de " + b.getVaste_Distance()
				+ "m a la redonda, pudiendo concentrarla en un punto u objeto. "
				+ "Todo se vuelve frío para congregar su energía anterior en un solo lugar, o viceversa.";
	}

	static Habilidad hastroUpter(PJ b) {
		try {
			usable.useActions(b.getActions() / 5, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return () -> "Puedes levantar hasta " + b.getMin() / 8 + " estructuras de hasta " + b.getVaste() / 8
				+ "m cuadrados a una distancia de " + b.getVaste_Distance() + "m. Dureza depende del material.";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////// LEIRZA

	static Habilidad regenerar(PJ b) {
		int necesario = b.getMaxHp() - b.getHp();
		int posible = b.getActions() * 2;
		if (posible >= necesario) {
			try {
				usable.useHp(necesario, b);
				usable.useActions(necesario / 2, b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				necesario -= b.getActions();
				usable.useHp(posible, b);
				usable.useActions(b.getActions(), b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return () -> "Se ha regenerado";
	}

	static Habilidad drenarVida(PJ b, boolean avg, int ataque) {
		try {
			int regen = ataque / 3;
			usable.useActions(regen, b);
			usable.useHp(regen / 2, b);
			return () -> ataque + "\nRegeneras " + regen + " acciones  y" + regen / 2
					+ "  puntos de vida con este ataque.";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
