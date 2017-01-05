package ppodds.rpg.pprpg.event;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;
import ppodds.rpg.pprpg.skill.mana.Mana;
import ppodds.rpg.pprpg.skill.mana.Mana.Regenerator;
import ppodds.rpg.pprpg.skill.mana.ManaBar;

public class PlayerJoin implements Listener
{
	
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		p.setHealthScale(20.0);
		
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
				
				Mana.addManaBar(p, 100, 10);
			}
			else
			{
				int maxMana = rs.getInt("智力") + 100;
				//之後再補魔力回復速率裝備加成
				Mana.addManaBar(p, maxMana, 10);
			}
			rs.close();
			stmt.close();
			con.close();
			
			File f = new File(pr.getDataFolder() + File.separator + "SkillData" + File.separator + p.getUniqueId().toString() + ".yml");
			if (!f.exists())
			{
				f.createNewFile();
			}
			
		}
		catch (SQLException ex)
		{
			pr.getLogger().warning(ex.toString());
		}
		catch (IOException ex)
		{
			pr.getLogger().warning(ex.toString());
		}
	}
}
