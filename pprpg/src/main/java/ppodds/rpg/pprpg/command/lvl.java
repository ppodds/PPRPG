package ppodds.rpg.pprpg.command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;

public class lvl implements CommandExecutor
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("lvl") && sender instanceof Player && args.length == 0)
		{
			int Str = 0;
			int Agi = 0;
			int Int = 0;
			int Exp = 0;
			int Point = 0;
			
			Player p = (Player) sender;

			try
			{
				Connection con = MySQL.con();
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				Str = rs.getInt("筋力");
				Agi = rs.getInt("敏捷");
				Int = rs.getInt("智力");
				Exp = rs.getInt("經驗");
				Point = rs.getInt("點數");
				
				rs.close();
				stmt.close();
				con.close();
				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2==============================&f基本屬性&2=============================="));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a等級 ： &f" ));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a經驗 ： &f" + Exp));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a生命 ： &f" + String.valueOf(p.getHealth()) + " / " + String.valueOf(p.getMaxHealth())));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a魔力 ： &f"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aStr ： &f" + Str));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aAgi ： &f" + Agi));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aInt ： &f" + Int));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a可用點數 ： &f" + Point));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2====================================================================="));
			}
			catch (SQLException e)
			{
				pr.getLogger().warning(e.toString());
			}
		}
		
		
		return false;
	}
}
