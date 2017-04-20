package ppodds.rpg.pprpg.command;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.skill.Skill;
import ppodds.rpg.pprpg.skill.mana.Mana;
import ppodds.rpg.pprpg.spells.Spell;

public class cast implements CommandExecutor
{
	private final static PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("cast") && sender instanceof Player)
		{
			Player p = (Player) sender;
			if (args.length < 1 || args.length > 1)
			{
				p.sendMessage("用法: /cast <技能名稱>");
			}
			else
			if (args.length == 1)
			{
				List<String> allSkillFolderName = Skill.getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));
				for (String skillFolderName : allSkillFolderName)
				{
					List<String> allSkillFileName = Skill.getAllSkillFileName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
					for (String skillFileName : allSkillFileName)
					{
						if (skillFileName.replace(".yml", "").equalsIgnoreCase(args[0]))
						{
							File playerData = new File(pr.getDataFolder() + File.separator + "SkillData" + File.separator + p.getUniqueId().toString() + ".yml");
							YamlConfiguration y = YamlConfiguration.loadConfiguration(playerData);
							File skillData = new File (pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName + File.separator + skillFileName);
							YamlConfiguration y1 = YamlConfiguration.loadConfiguration(skillData);
							if (y.getBoolean(y1.getString("name") + ".learn", false))
							{
								if (y.getBoolean(y1.getString("name") + ".inCooldown"))
								{
									p.sendMessage("技能在冷卻中!");
								}
								else
								{
									Skill skill = Skill.getSkill(Skill.getAllSkill(), y1.getString("name"));
									Class<?> c;
									try
									{
										c = Class.forName("ppodds.rpg.pprpg.spells" + y1.getString("class"));
										Spell s = (Spell) c.newInstance();
										s.cast(p, skill);
										p.sendMessage("使用了" + skill.getName() + ChatColor.WHITE + "!");
										Mana.removeMana(p, skill.getManaBase() + skill.getNextLevelMana() * y.getInt(y1.getString("name") + ".level"));
									}

									catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
									{
										pr.getLogger().warning(e.toString());
									}
								}
							}
							else
							{
								p.sendMessage("技能尚未學習!");
							}
						}
						else
						{
							p.sendMessage("無此技能!");
						}
					}
				}
			}
		}
		return false;
	}

}
