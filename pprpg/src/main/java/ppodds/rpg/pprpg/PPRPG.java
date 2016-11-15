package ppodds.rpg.pprpg;

import org.bukkit.plugin.java.JavaPlugin;

import ppodds.rpg.pprpg.command.lvl;
import ppodds.rpg.pprpg.event.PlayerJoin;
import ppodds.rpg.pprpg.mysql.MySQL;

public class PPRPG extends JavaPlugin
{
	@Override 
	public void onEnable()
	{
		MySQL.buildSQL();
		
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getCommand("lvl").setExecutor(new lvl());
	}
	@Override
	public void onDisable()
	{
		
	}
}
