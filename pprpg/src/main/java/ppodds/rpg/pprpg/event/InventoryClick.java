package ppodds.rpg.pprpg.event;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.skill.Skill;



public class InventoryClick implements Listener
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		List<String> allSkillFolderName = Skill.getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));
		try
		{
			for (String skillFolderName : allSkillFolderName)
			{
				if (e.getInventory().getTitle().equals(ChatColor.GREEN + "技能面板"))
				{
					e.setCancelled(true);
					try
					{
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals(skillFolderName))
						{
							e.getWhoClicked().closeInventory();
							pr.getServer().dispatchCommand(e.getWhoClicked(), "skill " + skillFolderName);
						}
					}
					catch (NullPointerException e1)
					{
						
					}
				}
				if (e.getInventory().getTitle().equals(ChatColor.GREEN + "技能面板 " + skillFolderName))
				{
					e.setCancelled(true);
					for(String skillName : Skill.getAllSkillName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName)))
					{
						try
						{
							if (e.getCurrentItem().getItemMeta().getDisplayName().equals(skillName))
							{
								e.getWhoClicked().closeInventory();
								pr.getServer().dispatchCommand(e.getWhoClicked(), "skill learn " + skillName);
							}
						}
						catch (NullPointerException e1)
						{
							
						}
					}
				}
			}
		}
		catch (Exception e1)
		{
			pr.getLogger().warning(e1.toString());
		}
	}
}
