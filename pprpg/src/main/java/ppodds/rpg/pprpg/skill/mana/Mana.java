package ppodds.rpg.pprpg.skill.mana;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.PPRPG;

public class Mana
{
	//魔力條的外部控制
	private static HashMap<String,ManaBar> manaBars = new HashMap<String,ManaBar>();
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	public HashMap<String, ManaBar> getManaBars()
	{
		return manaBars;
	}
	
	public static ManaBar getManaBar(Player player)
	{
		ManaBar bar = manaBars.get(player.getName());
		if (bar == null)
		{
			return null;
		}
		return bar;
	}
	
	public static void showMana(Player player)
	{
		ManaBar bar = getManaBar(player);
		if (bar != null)
		{
			player.sendMessage(ChatColor.BLUE + "魔力 : " + ChatColor.WHITE + String.valueOf(bar.getMana()) + " / " + String.valueOf(bar.getMaxMana()));
		}
	}
	
	public static void addManaBar(Player player,int maxMana,int regenAmount)
	{
		ManaBar bar = new ManaBar(player, maxMana, regenAmount);
		manaBars.put(player.getName(), bar);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pr, new Mana().new Regenerator(), 20, 200);
	}
	
	public static boolean addMana(Player player,int amount)
	{
		ManaBar bar = getManaBar(player);
		if (bar != null)
		{
			boolean r = bar.changeMana(amount);
			if (r)
			{
				Mana.showMana(player);
			}
			return r;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean removeMana(Player player, int amount)
	{
		return addMana(player, -amount);
	}
	
	public class Regenerator implements Runnable
	{
		@Override
		public void run()
		{
			for (ManaBar bar : manaBars.values())
			{
				boolean r = bar.regenerate();
				if (r)
				{
					Player p = bar.getPlayer();
					if (p != null)
					{
						showMana(p);
					}
				}
			}
		}		
	}
	
}
