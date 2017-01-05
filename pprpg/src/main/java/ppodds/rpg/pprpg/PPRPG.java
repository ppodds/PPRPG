package ppodds.rpg.pprpg;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import ppodds.rpg.pprpg.command.*;
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
		MySQL.buildSQL();
		
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntity(),this);
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getCommand("skill").setExecutor(new skill());
		getCommand("lvl").setExecutor(new lvl());
		getCommand("pt").setExecutor(new pt());
		getCommand("mana").setExecutor(new mana());
		getCommand("cast").setExecutor(new cast());
		
		File skillFolder = new File(getDataFolder() + File.separator + "Skill");
		if (!skillFolder.exists())
		{
			skillFolder.mkdir();
		}
	}
	@Override
	public void onDisable()
	{
		
	} 
}
