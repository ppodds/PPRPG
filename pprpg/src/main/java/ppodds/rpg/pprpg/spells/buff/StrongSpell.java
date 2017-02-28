package ppodds.rpg.pprpg.spells.buff;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.skill.Skill;

public class StrongSpell extends BuffSpell
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@Override
	public boolean cast(Player player,Skill skill)
	{
		//第一個試驗性技能
		try
		{
			File playerData = new File(pr.getDataFolder() + File.separator + "SkillData" + File.separator + player.getUniqueId().toString() + ".yml");
			YamlConfiguration y = YamlConfiguration.loadConfiguration(playerData);
			int duration = skill.getDurationBase() + skill.getNextLevelDuration() * y.getInt(skill.getName() + ".level");
			int cooldown = skill.getCooldownBase() + skill.getNextLevelCooldown() * y.getInt(skill.getName() + ".level");
			y.set(skill.getName() + ".inCooldown", true);
			y.set(skill.getName() + ".isUsing", true);
			y.save(playerData);
			Bukkit.getScheduler().runTaskLater(pr, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						y.set(skill.getName() + ".isUsing", false);
						y.save(playerData);
					}
					catch (IOException e)
					{
						pr.getLogger().warning(e.toString());
					}
				}
			}, duration);
			
			Bukkit.getScheduler().runTaskLater(pr, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						y.set(skill.getName() + ".inCooldown", false);
						y.save(playerData);
					}
					catch (IOException e)
					{
						pr.getLogger().warning(e.toString());
					}
				}
			}, cooldown);
			
		}
		catch (IllegalArgumentException | IOException e)
		{
			pr.getLogger().warning(e.toString());
		}
		
		return false;
	}

}
