package ppodds.rpg.pprpg.event;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.mysql.MySQL;
import ppodds.rpg.pprpg.skill.Skill;

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
				
				
				File playerData = new File(pr.getDataFolder() + File.separator + "SkillData" + File.separator + e.getDamager().getUniqueId() + ".yml");
				YamlConfiguration y = YamlConfiguration.loadConfiguration(playerData);
				List<String> allSkillFolderName = Skill.getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));
				
				
				double damage = e.getDamage() + Str * 0.4;
				
				Random random = new Random();
				
				if (random.nextDouble() <= Agi * 0.001)
				{
					p.sendMessage("你對" + e.getEntityType() + "造成了" + Math.floor(damage * 2) + "點傷害(爆擊)!(原傷害:" + Math.floor(e.getDamage()) + "點)");
					
					if (random.nextDouble() <= Agi * 0.0005)
					{
						p.sendMessage("(補充連擊)你對" + e.getEntityType() + "造成了" + Math.floor(damage * 2) + "點傷害(爆擊)!(原傷害:" + Math.floor(e.getDamage()) + "點)");
						e.setDamage(damage * 4);
						for (String skillFolderName : allSkillFolderName)
						{
							List<String> allSkillFileName = Skill.getAllSkillFileName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
							for (String skillFileName : allSkillFileName)
							{
								File skillData = new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName + File.separator + skillFileName);
								YamlConfiguration y1 = YamlConfiguration.loadConfiguration(skillData);
								if (y1.getString("class").equals(".buff.StrongSpell"))
								{
									Skill skill = Skill.getSkill(Skill.getAllSkill(), y1.getString("name"));
									if (y.getBoolean(skill.getName() + ".isUsing"))
									{
										double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect();
										e.setDamage(damage2);
										p.sendMessage("由於" + skill.getName() + ChatColor.WHITE + "效果，傷害提升至" + Math.floor(damage2) + "點!");
									}
								}
							}
						}
					}
					else
					{
						e.setDamage(damage * 2);
						for (String skillFolderName : allSkillFolderName)
						{
							List<String> allSkillFileName = Skill.getAllSkillFileName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
							for (String skillFileName : allSkillFileName)
							{
								File skillData = new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName + File.separator + skillFileName);
								YamlConfiguration y1 = YamlConfiguration.loadConfiguration(skillData);
								if (y1.getString("class").equals(".buff.StrongSpell"))
								{
									Skill skill = Skill.getSkill(Skill.getAllSkill(), y1.getString("name"));
									if (y.getBoolean(skill.getName() + ".isUsing"))
									{
										double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect();
										e.setDamage(damage2);
										p.sendMessage("由於" + skill.getName() + ChatColor.WHITE + "效果，傷害提升至" + Math.floor(damage2) + "點!");
									}
								}
							}
						}
					}
				}
				else
				{
				
					p.sendMessage("你對" + e.getEntityType() + "造成了" + Math.floor(damage) + "點傷害!(原傷害:" + Math.floor(e.getDamage()) + "點");
					
					if (random.nextDouble() <= Agi * 0.005)
					{
						p.sendMessage("(補充連擊)你對" + e.getEntityType() + "造成了" + Math.floor(damage) + "點傷害!(原傷害:" + Math.floor(e.getDamage()) + "點)");
						e.setDamage(damage * 2);
						for (String skillFolderName : allSkillFolderName)
						{
							List<String> allSkillFileName = Skill.getAllSkillFileName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
							for (String skillFileName : allSkillFileName)
							{
								File skillData = new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName + File.separator + skillFileName);
								YamlConfiguration y1 = YamlConfiguration.loadConfiguration(skillData);
								if (y1.getString("class").equals(".buff.StrongSpell"))
								{
									Skill skill = Skill.getSkill(Skill.getAllSkill(), y1.getString("name"));
									if (y.getBoolean(skill.getName() + ".isUsing"))
									{
										double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect();
										e.setDamage(damage2);
										p.sendMessage("由於" + skill.getName() + ChatColor.WHITE + "效果，傷害提升至" + Math.floor(damage2) + "點!");
									}
								}
							}
						}
					}
					else
					{
						e.setDamage(damage);
						for (String skillFolderName : allSkillFolderName)
						{
							List<String> allSkillFileName = Skill.getAllSkillFileName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
							for (String skillFileName : allSkillFileName)
							{
								File skillData = new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName + File.separator + skillFileName);
								YamlConfiguration y1 = YamlConfiguration.loadConfiguration(skillData);
								if (y1.getString("class").equals(".buff.StrongSpell"))
								{
									Skill skill = Skill.getSkill(Skill.getAllSkill(), y1.getString("name"));
									if (y.getBoolean(skill.getName() + ".isUsing"))
									{
										double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect();
										e.setDamage(damage2);
										p.sendMessage("由於" + skill.getName() + ChatColor.WHITE + "效果，傷害提升至" + Math.floor(damage2) + "點!");
									}
								}
							}
						}
					}
				}
				
			}
			catch(SQLException ex)
			{
				pr.getLogger().warning(ex.toString());
			}
			
			
		}
		//未測試
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
						p.sendMessage("成功迴避來自" + e.getDamager().getName() + "的攻擊!");
					}
				}
				else
				{
					if (damage > 0)
					{
						e.setDamage(damage);
						p.sendMessage("受到來自" + e.getDamager().getType().toString() + "的傷害" + Math.floor(damage) + "點!");
					}
					else
					{
						e.setDamage(1);
						p.sendMessage("受到來自" + e.getDamager().getType().toString() + "的傷害" + 1 + "點!");
					}
				}
			}
			catch (SQLException ex)
			{
				pr.getLogger().warning(ex.toString());
			}
		}
	}
}
