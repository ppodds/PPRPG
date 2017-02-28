package ppodds.rpg.pprpg.event;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.Consumer;

import com.google.common.base.Objects;

import ppodds.rpg.pprpg.PPRPG;

public class CreatureSpawn implements Listener
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e)
	{
		//生的是怪物變執行替換
		if (e.getEntity() instanceof Monster)
		{
			//判斷是不是已替換的怪物
			if (e.getEntity().getCustomName() == null)
			{
				File monsterSpawn = new File(pr.getDataFolder() + File.separator + "MonsterData" + File.separator + "MonsterSpawn.yml");
				YamlConfiguration y = YamlConfiguration.loadConfiguration(monsterSpawn);

				//輻射型生怪的準備
				double x1 = e.getEntity().getLocation().getX();
				double y1 = e.getEntity().getLocation().getY();
				double z1 = e.getEntity().getLocation().getZ();

				double x2 = y.getDouble("AutoRadiusSpawn." + e.getEntity().getWorld().getName() + ".Center.X");
				double y2 = y.getDouble("AutoRadiusSpawn." + e.getEntity().getWorld().getName() + ".Center.Y");
				double z2 = y.getDouble("AutoRadiusSpawn." + e.getEntity().getWorld().getName() + ".Center.Z");

				double distance = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2));

				int level = (int) Math.ceil(distance / y.getInt("AutoRadiusSpawn." + e.getEntity().getWorld().getName() + ".Radius"));
				
				//生態系生怪的準備
				Biome biome = e.getEntity().getWorld().getBiome(e.getEntity().getLocation().getBlockX(), e.getEntity().getLocation().getBlockZ());

				//判斷生態系
				if (!(y.getStringList("BiomeSpawn." + e.getEntity().getWorld().getName() + ".Spawn." + biome.toString()).size() == 0))
				{
					//抽選怪物
					List<String> monsterList = y.getStringList("BiomeSpawn." + e.getEntity().getWorld().getName() + ".Spawn." + biome.toString());
					int index = (int)Math.floor((Math.random() * monsterList.size() + 1)) - 1;
					String monsterName = monsterList.get(index);

					File monsterData = new File(pr.getDataFolder() + File.separator + "MonsterData" + File.separator + "Monsters.yml");
					YamlConfiguration m = YamlConfiguration.loadConfiguration(monsterData);
					
					//準備生成
					Class monsterClass = null;
					
					try
					{
						monsterClass = Class.forName("org.bukkit.entity." + m.getString(monsterName + ".EntityType"));
					}
					catch (ClassNotFoundException e1)
					{
						pr.getLogger().warning(e1.toString());
					}

					Consumer<Monster> c = monster -> monster.setCustomName(monsterName + " LV: " + String.valueOf(level));
					
					if (monsterClass != null)
					{
						//生成怪物
						Monster monster = e.getLocation().getWorld().spawn(e.getLocation(),monsterClass,c);
						((Damageable)monster).setMaxHealth(m.getInt(monsterName + ".Health") + m.getInt(monsterName + ".HealthScale") * level);
						((Damageable)monster).setHealth(m.getInt(monsterName + ".Health") + m.getInt(monsterName + ".HealthScale") * level);
					}
					//取消事件->替換
					e.setCancelled(true);
				}
				else
				{
					//抽選怪物
					List<String> monsterList = y.getStringList("BiomeSpawn." + e.getEntity().getWorld().getName() + ".Spawn.Default");
					int index = (int)Math.floor((Math.random() * monsterList.size() + 1)) - 1;
					String monsterName = monsterList.get(index);

					File monsterData = new File(pr.getDataFolder() + File.separator + "MonsterData" + File.separator + "Monsters.yml");
					YamlConfiguration m = YamlConfiguration.loadConfiguration(monsterData);

					//準備生成
					Class monsterClass = null;
					
					try
					{
						monsterClass = Class.forName("org.bukkit.entity." + m.getString(monsterName + ".EntityType"));
					}
					catch (ClassNotFoundException e1)
					{
						pr.getLogger().warning(e1.toString());
					}
					
					Consumer<Monster> c = monster -> monster.setCustomName(monsterName + " LV: " + String.valueOf(level));
					
					if (monsterClass != null)
					{
						//生成怪物
						Monster monster = e.getLocation().getWorld().spawn(e.getLocation(),monsterClass,c);
						((Damageable)monster).setMaxHealth(m.getInt(monsterName + ".Health") + m.getInt(monsterName + ".HealthScale") * level);
						((Damageable)monster).setHealth(m.getInt(monsterName + ".Health") + m.getInt(monsterName + ".HealthScale") * level);
					}
					//取消事件->替換
					e.setCancelled(true);

				}
			}
		}

	}
}
