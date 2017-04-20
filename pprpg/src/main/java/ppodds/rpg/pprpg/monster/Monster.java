package ppodds.rpg.pprpg.monster;

import java.io.File;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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
	private int expScale;
	private int money;
	private int moneyScale;
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	private ItemStack itemInHand;
	private List<HashMap<String,Double>> drop;
	



	private Monster(String name, int health, int healthScale, int damage, int damageScale, int hit, int hitScale,
			int dodge, int dodgeScale, int exp, int expScale, int money, int moneyScale, ItemStack helmet,
			ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack itemInHand,
			List<HashMap<String, Double>> drop)
	{
		super();
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
		this.expScale = expScale;
		this.money = money;
		this.moneyScale = moneyScale;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.itemInHand = itemInHand;
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
		int expScale = data.getInt(monsterName + ".ExpScale");;
		int money = data.getInt(monsterName + ".Money");
		int moneyScale = data.getInt(monsterName + ".MoneyScale");
		ItemStack helmet = data.getItemStack(monsterName + ".Helmet");
		ItemStack chestplate = data.getItemStack(monsterName + ".Chestplate");
		ItemStack leggings = data.getItemStack(monsterName + ".Leggings");
		ItemStack boots = data.getItemStack(monsterName + ".Boots");
		ItemStack itemInHand = data.getItemStack(monsterName + ".ItemInHand");
		
		List<HashMap<String,Double>> drop = (List<HashMap<String, Double>>) data.getList(monsterName + ".Drop");
		
		Monster monster = new Monster(name,health,healthScale,damage,damageScale,hit,hitScale,dodge,dodgeScale,exp,expScale,money,moneyScale,helmet,chestplate,leggings,boots,itemInHand,drop);
		
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



	public int getMoney() {
		return money;
	}

	public List<HashMap<String, Double>> getDrop() {
		return drop;
	}



	public int getExpScale() {
		return expScale;
	}


	public ItemStack getHelmet() {
		return helmet;
	}


	public ItemStack getChestplate() {
		return chestplate;
	}


	public ItemStack getLeggings() {
		return leggings;
	}


	public ItemStack getBoots() {
		return boots;
	}


	public ItemStack getItemInHand() {
		return itemInHand;
	}

	
}
