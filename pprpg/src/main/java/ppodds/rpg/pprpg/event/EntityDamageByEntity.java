package ppodds.rpg.pprpg.event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;

public class EntityDamageByEntity implements Listener
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
	{
		//玩家打生物/玩家
		if (e.getDamager() instanceof Player)
		{
			Player p = (Player)e.getDamager();
			try
			{
				int Str = 0;
				int Agi = 0;
				
				Connection con = MySQL.con();
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
				ResultSet rs = stmt.executeQuery(sql);
				
				rs.next();
				Str = rs.getInt("筋力");
				Agi = rs.getInt("敏捷");
				
				rs.close();
				stmt.close();
				con.close();
				
				double damage = e.getDamage() + Str * 0.4;
				
				Random random = new Random();
				
				if (random.nextDouble() <= Agi * 0.001)
				{
					p.sendMessage("你對" + e.getEntityType() + "造成了" + (damage * 2)+ "點傷害(爆擊)!(原傷害:" + e.getDamage() + "點)");
					
					if (random.nextDouble() <= Agi * 0.0005)
					{
						p.sendMessage("(補充連擊)你對" + e.getEntityType() + "造成了" + (damage * 2)+ "點傷害(爆擊)!(原傷害:" + e.getDamage() + "點)");
						e.setDamage(damage * 4);
					}
					else
					{
						e.setDamage(damage * 2);
					}
				}
				else
				{
				
					p.sendMessage("你對" + e.getEntityType() + "造成了" + damage + "點傷害!(原傷害:" + e.getDamage() + "點");
					
					if (random.nextDouble() <= Agi * 0.005)
					{
						p.sendMessage("(補充連擊)你對" + e.getEntityType() + "造成了" + damage + "點傷害!(原傷害:" + e.getDamage() + "點)");
					}
					else
					{
						e.setDamage(damage);
					}
				}
				
			}
			catch(SQLException ex)
			{
				pr.getLogger().warning(ex.toString());
			}
		}
		//玩家被生物/玩家打(80%完成) 未測試
		else
		if (e.getEntity() instanceof Player)
		{
			try
			{
				Player p = (Player)e.getEntity();

				int Str = 0;
				int Agi = 0;

				Connection con = MySQL.con();
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
				ResultSet rs = stmt.executeQuery(sql);

				rs.next();
				Str = rs.getInt("筋力");
				Agi = rs.getInt("敏捷");

				double damage = e.getDamage() - Str * 0.5;

				//骰子運算迴避 (迴避=Agi*0.4) 之後來做怪物的命中率  

				if (e.getDamager() instanceof Player)
				{
					String UUID = e.getDamager().getUniqueId().toString();
					String sql2 = "SELECT * FROM PLAYERSTATS WHERE UUID='" + UUID + "'";
					
					rs = stmt.executeQuery(sql2);
					rs.next();
					
					
					int agi = Agi - rs.getInt("敏捷");
					Random random = new Random();
					
					//骰子運算
					if (random.nextDouble() <= 1-(100/(100+agi*0.4)))
					{
						e.setDamage(0);
						p.sendMessage("成功迴避攻擊!");
					}
				}
				else
				{
					e.setDamage(damage);
				}
			}
			catch (SQLException ex)
			{
				pr.getLogger().warning(ex.toString());
			}
		}
	}
}
