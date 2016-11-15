package ppodds.rpg.pprpg.event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;

public class PlayerJoin implements Listener
{
	
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		
		try
		{
			Connection con = MySQL.con();
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next())
			{
				pr.getLogger().info("玩家資料不存在!建立玩家資料中!");
				String sql2 = "INSERT INTO PLAYERSTATS(UUID,筋力,敏捷,智力,經驗,點數)" + " VALUES('" + p.getUniqueId().toString() + "',0,0,0,0,25 )";
				stmt.executeUpdate(sql2);
				pr.getLogger().info(p.getName() + "的玩家資料已被建立成功!");
			}
			rs.close();
			stmt.close();
			con.close();
		}
		catch (SQLException ex)
		{
			pr.getLogger().warning(ex.toString());
		}
	}
}