package ppodds.rpg.pprpg.monster;

import java.io.File;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import ppodds.rpg.pprpg.PPRPG;

public class Monster
{
	//方便資料操作用類別
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	private String name;
	private int health;
	private int healthScale;
	private int damage;
	private int damageScale;
	private int hit;
	private int hitScale;
	private int dodge;
	private int dodgeScale;
	private int exp;
	private int moneyScale;
	private String ride;
	private String helmet;
	private String chestplate;
	private String leggings;
	private String boots;
	private String itemInHand;
	private int money;
	private List<HashMap<String,Double>> drop;
	

	
	private Monster(String name, int health, int healthScale, int damage, int damageScale, int hit, int hitScale,int dodge, int dodgeScale, int exp, int moneyScale, String ride, String helmet, String chestplate, String leggings, String boots, String itemInHand, int money, List<HashMap<String, Double>> drop)
	{
		this.name = name;
		this.health = health;
		this.healthScale = healthScale;
		this.damage = damage;
		this.damageScale = damageScale;
		this.hit = hit;
		this.hitScale = hitScale;
		this.dodge = dodge;
		this.dodgeScale = dodgeScale;
		this.exp = exp;
		this.moneyScale = moneyScale;
		this.ride = ride;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.itemInHand = itemInHand;
		this.money = money;
		this.drop = drop;
	}

	public static Monster getMonster(String monsterName)
	{

		File monsterData = new File(pr.getDataFolder() + File.separator + "MonsterData" + File.separator + "Monsters.yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(monsterData);

		String name = monsterName;
		int health = data.getInt(monsterName + ".Health");
		int healthScale = data.getInt(monsterName + ".HealthScale");
		int damage = data.getInt(monsterName + ".Damage");
		int damageScale = data.getInt(monsterName + ".DamageScale");
		int hit = data.getInt(monsterName + ".Hit");
		int hitScale = data.getInt(monsterName + ".HitScale");
		int dodge = data.getInt(monsterName + ".Dodge");
		int dodgeScale = data.getInt(monsterName + ".DodgeScale");
		int exp = data.getInt(monsterName + ".Exp");
		int expScale = data.getInt(monsterName + ".ExpScale");
		String ride = data.getString(monsterName + ".Ride");
		String helmet = data.getString(monsterName + ".Helmet");
		String chestplate = data.getString(monsterName + ".Chestplate");
		String leggings = data.getString(monsterName + ".Leggings");
		String boots = data.getString(monsterName + ".Boots");
		String itemInHand = data.getString(monsterName + ".ItemInHand");
		int money = data.getInt(monsterName + ".Money");
		int moneyScale = data.getInt(monsterName + ".MoneyScale");
		List<HashMap<String,Double>> drop = (List<HashMap<String, Double>>) data.getList(monsterName + ".Drop");
		
		Monster monster = new Monster(name,health,healthScale,damage,damageScale,hit,hitScale,dodge,dodgeScale,exp,expScale,ride,helmet,chestplate,leggings,boots,itemInHand,money,drop);
		
		return monster;
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return health;
	}

	public int getHealthScale() {
		return healthScale;
	}

	public int getDamage() {
		return damage;
	}

	public int getDamageScale() {
		return damageScale;
	}

	public int getHit() {
		return hit;
	}

	public int getHitScale() {
		return hitScale;
	}

	public int getDodge() {
		return dodge;
	}

	public int getDodgeScale() {
		return dodgeScale;
	}

	public int getExp() {
		return exp;
	}

	public int getMoneyScale() {
		return moneyScale;
	}

	public String getRide() {
		return ride;
	}

	public String getHelmet() {
		return helmet;
	}

	public String getChestplate() {
		return chestplate;
	}

	public String getLeggings() {
		return leggings;
	}

	public String getBoots() {
		return boots;
	}

	public String getItemInHand() {
		return itemInHand;
	}

	public int getMoney() {
		return money;
	}

	public List<HashMap<String, Double>> getDrop() {
		return drop;
	}
	
}
