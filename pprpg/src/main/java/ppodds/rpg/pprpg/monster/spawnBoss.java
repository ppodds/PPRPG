package ppodds.rpg.pprpg.monster;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.util.Consumer;

import ppodds.rpg.pprpg.PPRPG;

public class spawnBoss
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	private static File boss = new File(pr.getDataFolder() + File.separator + "MonsterData" + File.separator + "Boss.yml");
	private static File bossSpawn = new File(pr.getDataFolder() + File.separator + "MonsterData" + File.separator + "BossSpawn.yml");
	private static YamlConfiguration y1 = YamlConfiguration.loadConfiguration(boss);
	private static YamlConfiguration y2 = YamlConfiguration.loadConfiguration(bossSpawn);
	
	public static void startSpawnBoss()
	{
		for (World world : Bukkit.getWorlds())
		{
			List<String> bosses = y2.getStringList("AutoSpawn." + world.getName());
			if (!(bosses == null))
			{
				for (String bossName : bosses)
				{
					long period = y1.getLong(bossName + ".Spawn.period");
					double X = y1.getDouble(bossName + ".Spawn.X");
					double Y = y1.getDouble(bossName + ".Spawn.Y");
					double Z = y1.getDouble(bossName + ".Spawn.Z");
					Location l = new Location(world, X, Y, Z);
					int level = y1.getInt(bossName + ".Level");
					
					Bukkit.getScheduler().runTaskTimer(pr, new Runnable()
					{
						@Override
						public void run()
						{
							if (getAllEntityCustomName(world).indexOf(bossName + " LV: " + String.valueOf(level)) == -1)
							{
								Class monsterClass = null;
								
								try
								{
									monsterClass = Class.forName("org.bukkit.entity." + y1.getString(bossName + ".EntityType"));
								}
								catch (ClassNotFoundException e1)
								{
									pr.getLogger().warning(e1.toString());
								}

								Consumer<Monster> c = monster -> monster.setCustomName(bossName + " LV: " + String.valueOf(level));
								
								if (monsterClass != null)
								{
									//生成怪物
									Monster monster = world.spawn(l,monsterClass,c);
									((Damageable)monster).setMaxHealth(y1.getInt(bossName + ".Health") + y1.getInt(bossName + ".HealthScale") * level);
									((Damageable)monster).setHealth(y1.getInt(bossName + ".Health") + y1.getInt(bossName + ".HealthScale") * level);
									monster.getEquipment().setItemInHand(y1.getItemStack(bossName + ".ItemInHand"));
									monster.getEquipment().setHelmet(y1.getItemStack(bossName + ".Helmet"));
									monster.getEquipment().setChestplate(y1.getItemStack(bossName + ".Chestplate"));
									monster.getEquipment().setLeggings(y1.getItemStack(bossName + ".Leggings"));
									monster.getEquipment().setBoots(y1.getItemStack(bossName + ".Boots"));
								}
							}
						}
					}, 1200, period);
				}
			}
			
		}
	}

	private static List<String> getAllEntityCustomName(World world)
	{
		List<String> allEntityCustomName = new ArrayList<String>();
		for (Entity e : world.getEntities())
		{
			if (!(e.getCustomName() == null))
			{
				allEntityCustomName.add(e.getCustomName());
			}
		}
		return allEntityCustomName;
	}
}
