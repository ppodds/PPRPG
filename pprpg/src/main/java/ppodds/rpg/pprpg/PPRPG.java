package ppodds.rpg.pprpg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ppodds.rpg.pprpg.command.*;
import ppodds.rpg.pprpg.event.CreatureSpawn;
import ppodds.rpg.pprpg.event.EntityDamageByEntity;
import ppodds.rpg.pprpg.event.InventoryClick;
import ppodds.rpg.pprpg.event.PlayerJoin;
import ppodds.rpg.pprpg.event.PlayerLevelChange;
import ppodds.rpg.pprpg.monster.spawnBoss;
import ppodds.rpg.pprpg.mysql.MySQL;
import ppodds.rpg.pprpg.skill.Skill;

public class PPRPG extends JavaPlugin
{
	@Override 
	public void onEnable()
	{
		//SQL預備
		MySQL.buildSQL();
		
		spawnBoss.startSpawnBoss();
		
		//事件指令註冊
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntity(),this);
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new CreatureSpawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerLevelChange(), this);
		getCommand("skill").setExecutor(new skill());
		getCommand("lvl").setExecutor(new lvl());
		getCommand("pt").setExecutor(new pt());
		getCommand("mana").setExecutor(new mana());
		getCommand("cast").setExecutor(new cast());
		//檢查設定檔案
		checkDefaultFile();
		
	}
	@Override
	public void onDisable()
	{
		
	} 
	
	private void checkDefaultFile()
	{
		File skillDataFolder = new File(getDataFolder() + File.separator + "Skill");
		File playerDataFolder = new File(getDataFolder() + File.separator + "SkillData");
		File monsterDataFolder = new File(getDataFolder() + File.separator + "MonsterData");
		File monsterSpawn = new File(getDataFolder() + File.separator + "MonsterData" + File.separator + "MonsterSpawn.yml");
		File monsters = new File(getDataFolder() + File.separator + "MonsterData" + File.separator + "Monsters.yml");
		File boss = new File(getDataFolder() + File.separator + "MonsterData" + File.separator + "Boss.yml");
		File bossSpawn = new File(getDataFolder() + File.separator + "MonsterData" + File.separator + "BossSpawn.yml");
		//檔案不存在的創建手續
		try
		{
			if (!skillDataFolder.exists())
			{
				skillDataFolder.mkdir();
			}
			if (!playerDataFolder.exists())
			{
				playerDataFolder.mkdir();
			}
			if (!monsterDataFolder.exists())
			{
				monsterDataFolder.mkdir();
				
				monsterSpawn.createNewFile();
				YamlConfiguration y = YamlConfiguration.loadConfiguration(monsterSpawn);
				y.set("AutoRadiusSpawn.world.Radius", 50);
				y.set("AutoRadiusSpawn.world.Center.X", 0);
				y.set("AutoRadiusSpawn.world.Center.Y", 0);
				y.set("AutoRadiusSpawn.world.Center.Z", 0);
				List<String> l1 = new ArrayList<String>();
				l1.add("殭屍");
				List<String> l2 = new ArrayList<String>();
				l2.add("蜘蛛");
				y.set("BiomeSpawn.world.Spawn.Default", l1);
				y.set("BiomeSpawn.world.Spawn.DESERT", l2);
				y.save(monsterSpawn);
				monsters.createNewFile();
				YamlConfiguration y1 = YamlConfiguration.loadConfiguration(monsters);
				List<String> l3 = new ArrayList<String>();
				l3.add("IRON_SWORD 1");
				y1.set("殭屍.EntityType", "Zombie");
				y1.set("殭屍.Health", 30);
				y1.set("殭屍.HealthScale", 30);
				y1.set("殭屍.Damage", 2);
				y1.set("殭屍.DamageScale", 2);
				y1.set("殭屍.Hit", 2);
				y1.set("殭屍.HitScale", 2);
				y1.set("殭屍.Dodge", 2);
				y1.set("殭屍.DodgeScale", 2);
				y1.set("殭屍.Exp", 2);
				y1.set("殭屍.ExpScale", 2);
				y1.set("殭屍.Helmet", new ItemStack(Material.IRON_HELMET));
				y1.set("殭屍.Chestplate", new ItemStack(Material.IRON_CHESTPLATE));
				y1.set("殭屍.Leggings", new ItemStack(Material.IRON_LEGGINGS));
				y1.set("殭屍.Boots", new ItemStack(Material.IRON_BOOTS));
				y1.set("殭屍.ItemInHand", new ItemStack(Material.IRON_SWORD));
				y1.set("殭屍.Money", 3);
				y1.set("殭屍.MoneyScale", 3);
				y1.set("殭屍.Drop", l3);
				List<String> l4 = new ArrayList<String>();
				l4.add("BOW 1");
				y1.set("蜘蛛.EntityType", "Spider");
				y1.set("蜘蛛.Health", 30);
				y1.set("蜘蛛.HealthScale", 30);
				y1.set("蜘蛛.Damage", 2);
				y1.set("蜘蛛.DamageScale", 2);
				y1.set("蜘蛛.Hit", 2);
				y1.set("蜘蛛.HitScale", 2);
				y1.set("蜘蛛.Dodge", 2);
				y1.set("蜘蛛.DodgeScale", 2);
				y1.set("蜘蛛.Exp", 2);
				y1.set("蜘蛛.ExpScale", 2);
				y1.set("蜘蛛.Helmet", new ItemStack(Material.IRON_HELMET));
				y1.set("蜘蛛.Chestplate", new ItemStack(Material.IRON_CHESTPLATE));
				y1.set("蜘蛛.Leggings", new ItemStack(Material.IRON_LEGGINGS));
				y1.set("蜘蛛.Boots", new ItemStack(Material.IRON_BOOTS));
				y1.set("蜘蛛.ItemInHand", new ItemStack(Material.IRON_SWORD));
				y1.set("蜘蛛.Money", 3);
				y1.set("蜘蛛.MoneyScale", 3);
				y1.set("蜘蛛.Drop", l4);
				y1.save(monsters);
				boss.createNewFile();
				YamlConfiguration y2 = YamlConfiguration.loadConfiguration(boss);
				List<String> l5 = new ArrayList<String>();
				l5.add("IRON_SWORD 1");
				y2.set("(野外Boss)殭屍.EntityType", "Zombie");
				y2.set("(野外Boss)殭屍.Level", 10);
				y2.set("(野外Boss)殭屍.Health", 60);
				y2.set("(野外Boss)殭屍.HealthScale", 30);
				y2.set("(野外Boss)殭屍.Damage", 2);
				y2.set("(野外Boss)殭屍.DamageScale", 2);
				y2.set("(野外Boss)殭屍.Hit", 2);
				y2.set("(野外Boss)殭屍.HitScale", 2);
				y2.set("(野外Boss)殭屍.Dodge", 2);
				y2.set("(野外Boss)殭屍.DodgeScale", 2);
				y2.set("(野外Boss)殭屍.Exp", 2);
				y2.set("(野外Boss)殭屍.ExpScale", 2);
				y2.set("(野外Boss)殭屍.Helmet", new ItemStack(Material.IRON_HELMET));
				y2.set("(野外Boss)殭屍.Chestplate", new ItemStack(Material.IRON_CHESTPLATE));
				y2.set("(野外Boss)殭屍.Leggings", new ItemStack(Material.IRON_LEGGINGS));
				y2.set("(野外Boss)殭屍.Boots", new ItemStack(Material.IRON_BOOTS));
				y2.set("(野外Boss)殭屍.ItemInHand", new ItemStack(Material.IRON_SWORD));
				y2.set("(野外Boss)殭屍.Money", 3);
				y2.set("(野外Boss)殭屍.MoneyScale", 3);
				y2.set("(野外Boss)殭屍.Drop", l5);
				y2.set("(野外Boss)殭屍.Spawn.X", 0d);
				y2.set("(野外Boss)殭屍.Spawn.Y", 71d);
				y2.set("(野外Boss)殭屍.Spawn.Z", 0d);
				y2.set("(野外Boss)殭屍.Spawn.period", 1200);
				y2.save(boss);
				bossSpawn.createNewFile();
				YamlConfiguration y3 = YamlConfiguration.loadConfiguration(bossSpawn);
				List<String> l6 = new ArrayList<String>();
				l6.add("(野外Boss)殭屍");
				y3.set("AutoSpawn.world", l6);
				y3.save(bossSpawn);
			}
			if (!monsterSpawn.exists())
			{
				monsterSpawn.createNewFile();
				YamlConfiguration y = YamlConfiguration.loadConfiguration(monsterSpawn);
				y.set("AutoRadiusSpawn.world.Radius", 50);
				y.set("AutoRadiusSpawn.world.Center.X", 0);
				y.set("AutoRadiusSpawn.world.Center.Y", 0);
				y.set("AutoRadiusSpawn.world.Center.Z", 0);
				List<String> l1 = new ArrayList<String>();
				l1.add("殭屍");
				List<String> l2 = new ArrayList<String>();
				l2.add("蜘蛛");
				y.set("BiomeSpawn.world.Spawn.Default", l1);
				y.set("BiomeSpawn.world.Spawn.DESERT", l2);
				y.save(monsterSpawn);
			}
			if (!monsters.exists())
			{
				monsters.createNewFile();
				YamlConfiguration y1 = YamlConfiguration.loadConfiguration(monsters);
				List<String> l3 = new ArrayList<String>();
				l3.add("IRON_SWORD 1");
				y1.set("殭屍.EntityType", "Zombie");
				y1.set("殭屍.Health", 30);
				y1.set("殭屍.HealthScale", 30);
				y1.set("殭屍.Damage", 2);
				y1.set("殭屍.DamageScale", 2);
				y1.set("殭屍.Hit", 2);
				y1.set("殭屍.HitScale", 2);
				y1.set("殭屍.Dodge", 2);
				y1.set("殭屍.DodgeScale", 2);
				y1.set("殭屍.Exp", 2);
				y1.set("殭屍.ExpScale", 2);
				y1.set("殭屍.Helmet", new ItemStack(Material.IRON_HELMET));
				y1.set("殭屍.Chestplate", new ItemStack(Material.IRON_CHESTPLATE));
				y1.set("殭屍.Leggings", new ItemStack(Material.IRON_LEGGINGS));
				y1.set("殭屍.Boots", new ItemStack(Material.IRON_BOOTS));
				y1.set("殭屍.ItemInHand", new ItemStack(Material.IRON_SWORD));
				y1.set("殭屍.Money", 3);
				y1.set("殭屍.MoneyScale", 3);
				y1.set("殭屍.Drop", l3);
				List<String> l4 = new ArrayList<String>();
				l4.add("BOW 1");
				y1.set("蜘蛛.EntityType", "Spider");
				y1.set("蜘蛛.Health", 30);
				y1.set("蜘蛛.HealthScale", 30);
				y1.set("蜘蛛.Damage", 2);
				y1.set("蜘蛛.DamageScale", 2);
				y1.set("蜘蛛.Hit", 2);
				y1.set("蜘蛛.HitScale", 2);
				y1.set("蜘蛛.Dodge", 2);
				y1.set("蜘蛛.DodgeScale", 2);
				y1.set("蜘蛛.Exp", 2);
				y1.set("蜘蛛.ExpScale", 2);
				y1.set("蜘蛛.Helmet", new ItemStack(Material.IRON_HELMET));
				y1.set("蜘蛛.Chestplate", new ItemStack(Material.IRON_CHESTPLATE));
				y1.set("蜘蛛.Leggings", new ItemStack(Material.IRON_LEGGINGS));
				y1.set("蜘蛛.Boots", new ItemStack(Material.IRON_BOOTS));
				y1.set("蜘蛛.ItemInHand", new ItemStack(Material.IRON_SWORD));
				y1.set("蜘蛛.Money", 3);
				y1.set("蜘蛛.MoneyScale", 3);
				y1.set("蜘蛛.Drop", l4);
				y1.save(monsters);
			}
			if (!boss.exists())
			{
				boss.createNewFile();
				YamlConfiguration y2 = YamlConfiguration.loadConfiguration(boss);
				List<String> l5 = new ArrayList<String>();
				l5.add("IRON_SWORD 1");
				y2.set("(野外Boss)殭屍.EntityType", "Zombie");
				y2.set("(野外Boss)殭屍.Level", 10);
				y2.set("(野外Boss)殭屍.Health", 60);
				y2.set("(野外Boss)殭屍.HealthScale", 30);
				y2.set("(野外Boss)殭屍.Damage", 2);
				y2.set("(野外Boss)殭屍.DamageScale", 2);
				y2.set("(野外Boss)殭屍.Hit", 2);
				y2.set("(野外Boss)殭屍.HitScale", 2);
				y2.set("(野外Boss)殭屍.Dodge", 2);
				y2.set("(野外Boss)殭屍.DodgeScale", 2);
				y2.set("(野外Boss)殭屍.Exp", 2);
				y2.set("(野外Boss)殭屍.ExpScale", 2);
				y2.set("(野外Boss)殭屍.Helmet", new ItemStack(Material.IRON_HELMET));
				y2.set("(野外Boss)殭屍.Chestplate", new ItemStack(Material.IRON_CHESTPLATE));
				y2.set("(野外Boss)殭屍.Leggings", new ItemStack(Material.IRON_LEGGINGS));
				y2.set("(野外Boss)殭屍.Boots", new ItemStack(Material.IRON_BOOTS));
				y2.set("(野外Boss)殭屍.ItemInHand", new ItemStack(Material.IRON_SWORD));
				y2.set("(野外Boss)殭屍.Money", 3);
				y2.set("(野外Boss)殭屍.MoneyScale", 3);
				y2.set("(野外Boss)殭屍.Drop", l5);
				y2.set("(野外Boss)殭屍.Spawn.X", 0d);
				y2.set("(野外Boss)殭屍.Spawn.Y", 71d);
				y2.set("(野外Boss)殭屍.Spawn.Z", 0d);
				y2.set("(野外Boss)殭屍.Spawn.period", 1200);
				y2.save(boss);
			}
			if (!bossSpawn.exists())
			{
				bossSpawn.createNewFile();
				YamlConfiguration y3 = YamlConfiguration.loadConfiguration(bossSpawn);
				List<String> l6 = new ArrayList<String>();
				l6.add("(野外Boss)殭屍");
				y3.set("AutoSpawn.world", l6);
				y3.save(bossSpawn);
			}
		}
		catch (IOException e)
		{
			getLogger().warning(e.toString());
		}
	}
}
