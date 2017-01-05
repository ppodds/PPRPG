package ppodds.rpg.pprpg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.skill.mana.Mana;

public class mana implements CommandExecutor
{
	private static final PPRPG pr = (PPRPG)Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("mana") && sender instanceof Player)
		{
			Player p = (Player) sender;
			if (args.length <= 1)
			{
				
			}
			else
			if (args.length == 2 && args[0].equalsIgnoreCase("add"))
			{
				try
				{
					boolean r = Mana.addMana(p, Integer.parseInt(args[1]));
					if (r)
					{
						p.sendMessage("增加魔力成功!");
					}
					else
					{
						p.sendMessage("增加魔力失敗!");
					}
				}
				catch (NumberFormatException e)
				{
					pr.getLogger().toString();
				}
			}
			else
				if (args.length == 2 && args[0].equalsIgnoreCase("remove"))
				{
					try
					{
						boolean r = Mana.removeMana(p, Integer.parseInt(args[1]));
						if (r)
						{
							p.sendMessage("減少魔力成功!");
						}
						else
						{
							p.sendMessage("減少魔力失敗!");
						}
					}
					catch (NumberFormatException e)
					{
						pr.getLogger().toString();
					}
				}
		}
		return false;
	}
	
}
