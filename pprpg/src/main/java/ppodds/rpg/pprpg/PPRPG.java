package ppodds.rpg.pprpg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ppodds.rpg.pprpg.command.*;
import ppodds.rpg.pprpg.event.CreatureSpawn;
import ppodds.rpg.pprpg.event.EntityDamageByEntity;
import ppodds.rpg.pprpg.event.InventoryClick;
import ppodds.rpg.pprpg.event.PlayerJoin;
import ppodds.rpg.pprpg.mysql.MySQL;
import ppodds.rpg.pprpg.skill.Skill;

public class PPRPG extends JavaPlugin
{
	@Override 
	public void onEnable()
	{
		//SQL預備
		MySQL.buildSQL();
		
		//事件指令註冊
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntity(),this);
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new CreatureSpawn(), this);
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
				File monsterSpawn = new File(getDataFolder() + File.separator + "MonsterData" + File.separator + "MonsterSpawn.yml");
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
				File monsters = new File(getDataFolder() + File.separator + "MonsterData" + File.separator + "Monsters.yml");
				monsterSpawn.createNewFile();
				YamlConfiguration y1 = YamlConfiguration.loadConfiguration(monsters);
				List<String> l3 = new ArrayList<String>();
				l3.add("IRON_SWORD 1");
				y1.set("殭屍.EntityType", "ZOMBIE");
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
				y1.set("殭屍.Ride", "蜘蛛");
				y1.set("殭屍.Helmet", "IRON_HELMET");
				y1.set("殭屍.Chestplate", "IRON_CHESTPLATE");
				y1.set("殭屍.Leggings", "IRON_LEGGINGS");
				y1.set("殭屍.Boots", "IRON_BOOTS");
				y1.set("殭屍.ItemInHand", "IRON_SWORD");
				y1.set("殭屍.Money", 3);
				y1.set("殭屍.MoneyScale", 3);
				y1.set("殭屍.Drop", l3);
				List<String> l4 = new ArrayList<String>();
				l4.add("BOW 1");
				y1.set("蜘蛛.EntityType", "SPIDER");
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
				y1.set("蜘蛛.Ride", "骷髏");
				y1.set("蜘蛛.Helmet", "IRON_HELMET");
				y1.set("蜘蛛.Chestplate", "IRON_CHESTPLATE");
				y1.set("蜘蛛.Leggings", "IRON_LEGGINGS");
				y1.set("蜘蛛.Boots", "IRON_BOOTS");
				y1.set("蜘蛛.ItemInHand", "IRON_SWORD");
				y1.set("蜘蛛.Money", 3);
				y1.set("蜘蛛.MoneyScale", 3);
				y1.set("蜘蛛.Drop", l4);
				y1.save(monsters);
			}
		}
		catch (IOException e)
		{
			getLogger().warning(e.toString());
		}
	}
}
