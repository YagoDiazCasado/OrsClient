package main.java.com.ors.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import main.java.com.ors.utiles.EnumsDeItems.BasicHitter;
import main.java.com.ors.utiles.EnumsDeItems.CharacterTypes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PJ implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("nombre") // Puedes dejarlo como "name" si prefieres el original
	private String name;

	private Race race;

	@JsonProperty("poder")
	private String power;

	@JsonProperty("tipo_personaje")
	private CharacterTypes characterType;

	@JsonProperty("adventureName")
	private String adventureName;

	private Item weapon;

	@JsonProperty("tipos_corporales")
	private Set<BodyType> bodyTypes = new HashSet<>();

	@JsonProperty("habilidades")
	private Set<Skill> skills = new HashSet<>();

	private List<Inventory> inventario = new ArrayList<>();

	private Equipment equipment;

	@JsonProperty("ataque_basico")
	private BasicHitter basicHitter = BasicHitter.FISTS;

	@JsonProperty("puntos_inspiracion")
	private int inspirationPoints = 0;

	@JsonProperty("activo")
	private boolean able = true;

	private byte[] profile;

	@JsonProperty("atlético")
	private int atl = 1;

	@JsonProperty("fuerza")
	private int str = 1;

	@JsonProperty("resistencia")
	private int end = 1;

	@JsonProperty("mente")
	private int min = 1;

	@JsonProperty("destreza")
	private int dex = 1;

	@JsonIgnore
	private double modA, modS, modE, modM, modD;

	@JsonIgnore
	private List<Double> mods = new ArrayList<>();

	@JsonProperty("modificadores")
	public List<Double> getAllMods() {
		return new ArrayList<>(List.of(modA, modS, modE, modM, modD));
	}

	@JsonProperty("xp_atlético")
	private int xpA = 0;

	@JsonProperty("xp_fuerza")
	private int xpS = 0;

	@JsonProperty("xp_resistencia")
	private int xpE = 0;

	@JsonProperty("xp_mente")
	private int xpM = 0;

	@JsonProperty("xp_destreza")
	private int xpD = 0;

	@JsonProperty("glimmers")
	private double glimmers = 0;

	@JsonProperty("peso")
	private double weight;

	@JsonProperty("carga_max")
	private double maxCarry = 100;

	@JsonProperty("carga_actual")
	private double currentCarry = 0.0;

	@JsonProperty("pérdida_peso")
	private double weightLose = 0;

	@JsonIgnore
	private int maxHp, maxActions, maxKcal;

	@JsonProperty("hp_actual")
	private int hp = 1;

	@JsonProperty("acciones_actuales")
	private int actions = 1;

	@JsonProperty("kcal_actuales")
	private int kcal = 1;

	@JsonIgnore
	private String[] dices = { "atl", "str", "end", "min", "dex", "acr", "vas", "per", "cha" };

	@JsonIgnore
	private double leftStrong;

	@JsonIgnore
	private int speed, preception, charisma, acrobatics, balance_Lv, vaste, vaste_Distance;

	public PJ(String name, Race race, String power, byte[] profile, CharacterTypes characterType, Adventure adventure,
			boolean able, int atl, int str, int end, int min, int dex, double glimmers) {
		this.name = name;
		this.race = race;
		this.power = power;
		this.profile = profile;
		this.characterType = characterType;
		this.adventureName = adventure.getAdventureName();
		this.able = able;
		this.atl = atl;
		this.str = str;
		this.end = end;
		this.min = min;
		this.dex = dex;
		this.glimmers = glimmers;
	}

	@Override
	public String toString() {
		return name;
	}

	public String showInfo() {
		return "\n--------------------------------------------------\nPJ name=" + name + ", race=" + race.getName()
				+ ", power=" + power + ", \ncharacterType=" + characterType + ", adventure=" + adventureName
				+ ", weapon=" + weapon + ", \nbodyTypes=" + bodyTypes.toString() + ", \ninspirationPoints="
				+ inspirationPoints + ", able=" + able + ", \natl=" + atl + ", \nstr=" + str + ", \nend=" + end
				+ ", \nmin=" + min + ", \ndex=" + dex + ", \nmodA=" + modA + ", modS=" + modS + ", modE=" + modE
				+ ", modM=" + modM + ", modD=" + modD + ", \nxpA=" + xpA + ", xpS=" + xpS + ", xpE=" + xpE + ", xpM="
				+ xpM + ", xpD=" + xpD + ", \nglimmers=" + glimmers + ", weight=" + weight + ", \nmaxCarry=" + maxCarry
				+ ", currentCarry=" + currentCarry + ", weightLose=" + weightLose + ", \nmaxHp=" + maxHp
				+ ", maxActions=" + maxActions + ", maxKcal=" + maxKcal + ", \nhp=" + hp + ", actions=" + actions
				+ ", kcal=" + kcal + ", \ndices=" + Arrays.toString(dices) + ", \nleftStrong=" + leftStrong
				+ ", \nspeed=" + speed + ", \npreception=" + preception + ", \ncharisma=" + charisma + ", \nacrobatics="
				+ acrobatics + ", \nbalance_Lv=" + balance_Lv + ", vaste=" + vaste + ", vaste_Distance="
				+ vaste_Distance + "]\n--------------------------------------------------\n\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		PJ otro = (PJ) obj;
		return this.name.equals(otro.name);
	}

	public PJ(String n) {
		this.name = n;
	}

	public PJ() {
		// TODO Auto-generated constructor stub
	}

	public BasicHitter getBasicHitter() {
		return basicHitter;
	}

	public void setBasicHitter(BasicHitter basicHitter) {
		this.basicHitter = basicHitter;
	}

	public int getInspirationPoints() {
		return inspirationPoints;
	}

	public void setInspirationPoints(int inspirationPoints) {
		this.inspirationPoints = inspirationPoints;
	}

	public boolean isAble() {
		return able;
	}

	public void setAble(boolean able) {
		this.able = able;
	}

	public int getAtl() {
		return atl;
	}

	public void setAtl(int atl) {
		this.atl = atl;
	}

	public int getStr() {
		return str;
	}

	public void setStr(int str) {
		this.str = str;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getDex() {
		return dex;
	}

	public void setDex(int dex) {
		this.dex = dex;
	}

	public int getXpA() {
		return xpA;
	}

	public void setXpA(int xpA) {
		this.xpA = xpA;
	}

	public int getXpS() {
		return xpS;
	}

	public void setXpS(int xpS) {
		this.xpS = xpS;
	}

	public int getXpE() {
		return xpE;
	}

	public void setXpE(int xpE) {
		this.xpE = xpE;
	}

	public int getXpM() {
		return xpM;
	}

	public void setXpM(int xpM) {
		this.xpM = xpM;
	}

	public int getXpD() {
		return xpD;
	}

	public void setXpD(int xpD) {
		this.xpD = xpD;
	}

	public double getGlimmers() {
		return glimmers;
	}

	public void setGlimmers(double glimmers) {
		this.glimmers = glimmers;
	}

	public double getMaxCarry() {
		return maxCarry;
	}

	public void setMaxCarry(double maxCarry) {
		this.maxCarry = maxCarry;
	}

	public double getCurrentCarry() {
		return currentCarry;
	}

	public void setCurrentCarry(double currentCarry) {
		this.currentCarry = currentCarry;
	}

	public double getWeightLose() {
		return weightLose;
	}

	public void setWeightLose(double weightLose) {
		this.weightLose = weightLose;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getActions() {
		return actions;
	}

	public void setActions(int actions) {
		this.actions = actions;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
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

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public CharacterTypes getCharacterType() {
		return characterType;
	}

	public void setCharacterType(CharacterTypes characterType) {
		this.characterType = characterType;
	}

	public Item getWeapon() {
		return weapon;
	}

	public String getAdventureName() {
		return adventureName;
	}

	public void setAdventureName(String name) {
		this.adventureName = name;
	}

	public void setWeapon(Item weapon) {
		this.weapon = weapon;
	}

	public Set<BodyType> getBodyTypes() {
		return bodyTypes;
	}

	public void setBodyTypes(Set<BodyType> bodyTypes) {
		this.bodyTypes = bodyTypes;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public List<Inventory> getInventario() {
		return inventario;
	}

	public void setInventario(List<Inventory> inventario) {
		this.inventario = inventario;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public double getModA() {
		return modA;
	}

	public void setModA(double modA) {
		this.modA = modA;
	}

	public double getModS() {
		return modS;
	}

	public void setModS(double modS) {
		this.modS = modS;
	}

	public double getModE() {
		return modE;
	}

	public void setModE(double modE) {
		this.modE = modE;
	}

	public double getModM() {
		return modM;
	}

	public void setModM(double modM) {
		this.modM = modM;
	}

	public double getModD() {
		return modD;
	}

	public void setModD(double modD) {
		this.modD = modD;
	}

	public List<Double> getMods() {
		return mods;
	}

	public void setMods(List<Double> mods) {
		this.mods = mods;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public void setMaxActions(int maxActions) {
		this.maxActions = maxActions;
	}

	public int getMaxKcal() {
		return maxKcal;
	}

	public void setMaxKcal(int maxKcal) {
		this.maxKcal = maxKcal;
	}

	public String[] getDices() {
		return dices;
	}

	public void setDices(String[] dices) {
		this.dices = dices;
	}

	public double getLeftStrong() {
		return leftStrong;
	}

	public void setLeftStrong(double leftStrong) {
		this.leftStrong = leftStrong;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPreception() {
		return preception;
	}

	public void setPreception(int preception) {
		this.preception = preception;
	}

	public int getCharisma() {
		return charisma;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public int getAcrobatics() {
		return acrobatics;
	}

	public void setAcrobatics(int acrobatics) {
		this.acrobatics = acrobatics;
	}

	public int getBalance_Lv() {
		return balance_Lv;
	}

	public void setBalance_Lv(int balance_Lv) {
		this.balance_Lv = balance_Lv;
	}

	public int getVaste() {
		return vaste;
	}

	public void setVaste(int vaste) {
		this.vaste = vaste;
	}

	public int getVaste_Distance() {
		return vaste_Distance;
	}

	public void setVaste_Distance(int vaste_Distance) {
		this.vaste_Distance = vaste_Distance;
	}

}
