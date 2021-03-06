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
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.*;
import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;
import ppodds.rpg.pprpg.skill.mana.Mana;

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
			int Point = 0;
			int SkillPoint = 0;
			
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
				Point = rs.getInt("能力點");
				SkillPoint = rs.getInt("技能點");
				
				rs.close();
				stmt.close();
				con.close();
				

				
				IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \" \",\"extra\":[{\"text\": \"§a[分配點數]\",\"clickEvent\": {\"action\": \"run_command\",\"value\": \"/pt\"}}]}");
				PacketPlayOutChat packet = new PacketPlayOutChat(icbc, (byte) 1);
				CraftPlayer cp = (CraftPlayer)p;
				
				

				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2==============================&f基本屬性&2=============================="));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a等級 ： &f" + String.valueOf(p.getLevel())));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a經驗 ： &f" + String.valueOf((int)Math.floor(p.getExp() * 100) + "%")));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a生命 ： &f" + String.valueOf(p.getHealth()) + " / " + String.valueOf(p.getMaxHealth())));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a魔力 ： &f" + String.valueOf(Mana.getManaBar(p).getMana()) + " / " + String.valueOf(Mana.getManaBar(p).getMaxMana())));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aStr ： &f" + Str));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aAgi ： &f" + Agi));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aInt ： &f" + Int));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a可用能力點 ： &f" + Point ));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a可用技能點 ： &f" + SkillPoint ));
				cp.getHandle().playerConnection.sendPacket(packet);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2====================================================================="));
			}
			catch (SQLException e)
			{
				pr.getLogger().warning(e.toString());
			}
			catch (Exception e)
			{
				pr.getLogger().warning(e.toString());
			}
			
		}
		
		
		return false;
	}
}
