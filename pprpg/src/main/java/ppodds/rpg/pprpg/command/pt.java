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
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;

public class pt implements CommandExecutor
{
	
	private static final PPRPG pr = (PPRPG)Bukkit.getPluginManager().getPlugin("PPRPG");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Connection con = MySQL.con();
		
		int Str = 0;
		int Agi = 0;
		int Int = 0;
		int Exp = 0;
		int Point = 0;
		
		if (label.equalsIgnoreCase("pt") &&  sender instanceof Player && args.length == 0)
		{
			
			
			Player p = (Player) sender;
			
			try
			{
				String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
				Statement stmt = con.createStatement();
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
				
				IChatBaseComponent Stri = ChatSerializer.a("{\"text\": \" \",\"extra\":[{\"text\": \"§a[分配點數]\",\"clickEvent\": {\"action\": \"run_command\",\"value\": \"/pt str\"}}]}");
				IChatBaseComponent Agii = ChatSerializer.a("{\"text\": \" \",\"extra\":[{\"text\": \"§a[分配點數]\",\"clickEvent\": {\"action\": \"run_command\",\"value\": \"/pt agi\"}}]}");
				IChatBaseComponent Inti = ChatSerializer.a("{\"text\": \" \",\"extra\":[{\"text\": \"§a[分配點數]\",\"clickEvent\": {\"action\": \"run_command\",\"value\": \"/pt int\"}}]}");
				
				PacketPlayOutChat packetStr = new PacketPlayOutChat(Stri, (byte) 1);
				PacketPlayOutChat packetAgi = new PacketPlayOutChat(Agii, (byte) 1);
				PacketPlayOutChat packetInt = new PacketPlayOutChat(Inti, (byte) 1);
				
				CraftPlayer cp = (CraftPlayer)p;
				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2==============================&f基本屬性&2=============================="));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a等級 ： &f" ));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a經驗 ： &f" + Exp));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a生命 ： &f" + String.valueOf(p.getHealth()) + " / " + String.valueOf(p.getMaxHealth())));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a魔力 ： &f"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aStr ： &f" + Str));
				cp.getHandle().playerConnection.sendPacket(packetStr);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aAgi ： &f" + Agi));
				cp.getHandle().playerConnection.sendPacket(packetAgi);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &aInt ： &f" + Int));
				cp.getHandle().playerConnection.sendPacket(packetInt);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a可用點數 ： &f" + Point ));
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
		
		
		
		if (label.equalsIgnoreCase("pt") &&  sender instanceof Player && args.length == 1)
		{
			
			try
			{
				Statement stmt = con.createStatement();
				Player p = (Player) sender;
				
				String arg = args[0];
				
				String sqls = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
				ResultSet rs = stmt.executeQuery(sqls);
				rs.next();
				Str = rs.getInt("筋力");
				Agi = rs.getInt("敏捷");
				Int = rs.getInt("智力");
				Exp = rs.getInt("經驗");
				Point = rs.getInt("點數");
				rs.close();
				
				switch (arg)
				{
				case "str":
					if (Point>0)
					{
						String sqlStr = "UPDATE PlayerStats SET 筋力=" + (Str+1) + ",敏捷=" + Agi + ",智力=" + Int + ",經驗=" + Exp + ",點數=" + (Point-1) + " WHERE UUID='" + p.getUniqueId().toString() + "'";
						stmt.executeUpdate(sqlStr);
						p.performCommand("pt");

						stmt.close();
						con.close();
						
						p.setMaxHealth(20 + Str);
						p.sendMessage("當前最大生命有" + (20 + Str) + "點!");
					}
					else
					{
						p.sendMessage(ChatColor.RED + "剩餘點數不足!");
					}
					
					break;
				case "agi":
					if (Point>0)
					{
						String sqlAgi = "UPDATE PlayerStats SET 筋力=" + Str + ",敏捷=" + (Agi+1) + ",智力=" + Int + ",經驗=" + Exp + ",點數=" + (Point-1) + " WHERE UUID='" + p.getUniqueId().toString() + "'";
						stmt.executeUpdate(sqlAgi);
						p.performCommand("pt");
					
						stmt.close();
						con.close();
						
						p.setWalkSpeed(0.2f + Agi*0.0002f);
						p.sendMessage("當前移動速度為" + (0.2f + Agi*0.0002f) + "!");
						p.sendMessage("當前迴避率為" + (1-(100/(100+Agi*0.4)) + "!"));
						p.sendMessage("當前爆擊率為" + Agi*0.001 + "!");
						p.sendMessage("當前補充連擊率為" + Agi*0.0005 + "!");
					}
					else
					{
						p.sendMessage(ChatColor.RED + "剩餘點數不足!");
					}
					
					break;
				case "int":
					if (Point>0)
					{
						String sqlInt = "UPDATE PlayerStats SET 筋力=" + Str + ",敏捷=" + Agi + ",智力=" + (Int+1) + ",經驗=" + Exp + ",點數=" + (Point-1) + " WHERE UUID='" + p.getUniqueId().toString() + "'";
						stmt.executeUpdate(sqlInt);
						p.performCommand("pt");
					
						stmt.close();
						con.close();
					}
					else
					{
						p.sendMessage(ChatColor.RED + "剩餘點數不足!");
					}
					
					break;
				default:
					p.sendMessage(ChatColor.RED + "指令用法錯誤!");
					
					stmt.close();
					con.close();
				}
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
