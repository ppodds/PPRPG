package ppodds.rpg.pprpg.event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;

public class PlayerLevelChange implements Listener
{
	private static final PPRPG pr = (PPRPG)Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@EventHandler
	public void onPlayerLevelChange(PlayerLevelChangeEvent e)
	{
		try
		{
			Connection con = MySQL.con();

			int Str = 0;
			int Agi = 0;
			int Int = 0;
			int Exp = 0;
			int Point = 0;
			int SkillPoint = 0;

			Player p = e.getPlayer();

			Statement stmt = con.createStatement();
			
			String sqls = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
			ResultSet rs = stmt.executeQuery(sqls);
			rs.next();
			Str = rs.getInt("筋力");
			Agi = rs.getInt("敏捷");
			Int = rs.getInt("智力");
			Point = rs.getInt("能力點");
			SkillPoint = rs.getInt("技能點");
			rs.close();
			
			String sqlStr = "UPDATE PlayerStats SET 筋力=" + Str + ",敏捷=" + Agi + ",智力=" + Int + ",能力點=" + (Point+5) + ",技能點=" + (SkillPoint+3) + " WHERE UUID='" + p.getUniqueId().toString() + "'";
			stmt.executeUpdate(sqlStr);
			
			stmt.close();
			con.close();
			
			p.sendMessage("恭喜升級!獲得了5點能力點和3點技能點!");
		}
		catch (SQLException e1)
		{
			pr.getLogger().warning(e1.toString());
		}
	}
}
