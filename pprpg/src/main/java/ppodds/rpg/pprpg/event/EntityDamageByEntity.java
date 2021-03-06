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
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Monster;
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
		//玩家打實體
		if (e.getDamager() instanceof Player)
		{
			Player p = (Player)e.getDamager();
			//玩家打生物
			if (e.getEntity() instanceof Creature)
			{
				//玩家打怪物
				if (e.getEntity() instanceof Monster)
				{
					try
					{
						int Str = 0;
						int Agi = 0;
						int Int = 0;

						Connection con = MySQL.con();
						Statement stmt = con.createStatement();
						String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
						ResultSet rs = stmt.executeQuery(sql);

						rs.next();
						Str = rs.getInt("筋力");
						Agi = rs.getInt("敏捷");
						Int = rs.getInt("智力");

						rs.close();
						stmt.close();
						con.close();


						File playerData = new File(pr.getDataFolder() + File.separator + "SkillData" + File.separator + e.getDamager().getUniqueId() + ".yml");
						YamlConfiguration y = YamlConfiguration.loadConfiguration(playerData);
						List<String> allSkillFolderName = Skill.getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));


						double damage = e.getDamage() + Str * 0.4;

						Random random = new Random();
						
						Monster monster = (Monster) e.getEntity();
						String monsterName = monster.getCustomName().substring(0,monster.getCustomName().indexOf(" "));
						int level = Integer.parseInt((monster.getCustomName().substring(monster.getCustomName().lastIndexOf(" "))).trim());
						ppodds.rpg.pprpg.monster.Monster monsterData = ppodds.rpg.pprpg.monster.Monster.getMonster(monsterName);
						
						//命中公式平衡性待改良
						double rate = Agi * 1.2 - (monsterData.getDodge() + monsterData.getDodgeScale() * level);

						if (random.nextDouble() >= 1-(100/(100+rate)))
						{
							if (random.nextDouble() <= Agi * 0.001)
							{
								e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 100);

								if (random.nextDouble() <= Agi * 0.0005)
								{
									e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 1000);
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
													double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
													e.setDamage(damage2);
													e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
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
													double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
													e.setDamage(damage2);
													e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
												}
											}
										}
									}
								}
							}
							else
							{

								e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 100);

								if (random.nextDouble() <= Agi * 0.005)
								{
									e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 1000);
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
													double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
													e.setDamage(damage2);
													e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
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
													double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
													e.setDamage(damage2);
													e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
												}
											}
										}
									}
								}
							}
						}
						else
						{
							e.setDamage(0);
							e.getEntity().getWorld().spawnParticle(Particle.PORTAL, e.getEntity().getLocation(), 100);
						}
						
					}
					catch(SQLException ex)
					{
						pr.getLogger().warning(ex.toString());
					}
				}
				//玩家打其他生物
				else
				{
					try
					{
						int Str = 0;
						int Agi = 0;
						int Int = 0;

						Connection con = MySQL.con();
						Statement stmt = con.createStatement();
						String sql = "SELECT * FROM PLAYERSTATS WHERE UUID='" + p.getUniqueId().toString() + "'";
						ResultSet rs = stmt.executeQuery(sql);

						rs.next();
						Str = rs.getInt("筋力");
						Agi = rs.getInt("敏捷");
						Int = rs.getInt("智力");

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
							e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 100);

							if (random.nextDouble() <= Agi * 0.0005)
							{
								e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 1000);
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
												double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
												e.setDamage(damage2);
												e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
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
												double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
												e.setDamage(damage2);
												e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 1000);
											}
										}
									}
								}
							}
						}
						else
						{

							e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 100);

							if (random.nextDouble() <= Agi * 0.005)
							{
								e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 1000);
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
												double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
												e.setDamage(damage2);
												e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
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
												double damage2 = e.getDamage() + skill.getEffectBase() + y.getInt(skill.getName() + ".level") * skill.getNextLevelEffect() + skill.getStrScale() * Str + skill.getAgiScale() * Agi + skill.getIntScale() * Int;
												e.setDamage(damage2);
												e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation(), 100);
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
			}
		}
		//怪物打實體
		if (e.getDamager() instanceof Monster)
		{
			//怪物打玩家
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
					
					Monster monster = (Monster) e.getDamager();
					String monsterName = monster.getCustomName().substring(0,monster.getCustomName().indexOf(" "));
					int level = Integer.parseInt((monster.getCustomName().substring(monster.getCustomName().lastIndexOf(" "))).trim());
					ppodds.rpg.pprpg.monster.Monster monsterData = ppodds.rpg.pprpg.monster.Monster.getMonster(monsterName);

					double damage = (monsterData.getDamage() + monsterData.getDamageScale() * level) - Str * 0.5;
					
					Random random = new Random();
					
					//迴避公式平衡性待改良
					double rate = (monsterData.getHit() + monsterData.getHitScale() * level) - Agi * 1.2;
					
					if (random.nextDouble() >= 1-(100/(100+rate)))
					{
						e.setDamage(0);
						e.getEntity().getWorld().spawnParticle(Particle.PORTAL, e.getEntity().getLocation(), 100);
					}
					else
					{
						if (damage > 0)
						{
							e.setDamage(damage);
							e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 100);
						}
						else
						{
							e.setDamage(1);
							e.getEntity().getWorld().spawnParticle(Particle.FLAME, e.getEntity().getLocation(), 100);
						}
					}
				}
				catch (SQLException ex)
				{
					pr.getLogger().warning(ex.toString());
				}
			}
			//怪物打其他實體
			else
			{
				Monster monster = (Monster) e.getDamager();
				String monsterName = monster.getCustomName().substring(0,monster.getCustomName().indexOf(" "));
				int level = Integer.parseInt((monster.getCustomName().substring(monster.getCustomName().lastIndexOf(" "))).trim());
				ppodds.rpg.pprpg.monster.Monster monsterData = ppodds.rpg.pprpg.monster.Monster.getMonster(monsterName);
				e.setDamage(monsterData.getDamage() + monsterData.getDamageScale() * level);
			}
		}
	}
	
}
