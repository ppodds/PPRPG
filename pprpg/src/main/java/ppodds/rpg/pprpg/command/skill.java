package ppodds.rpg.pprpg.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ppodds.rpg.pprpg.PPRPG;
import ppodds.rpg.pprpg.skill.Skill;



public class skill implements CommandExecutor
{
	private final static PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("skill") && sender instanceof Player)
		{
			Player p = (Player) sender;
			File f = new File(pr.getDataFolder() + File.separator + "SkillData" + File.separator + p.getUniqueId().toString() + ".yml");
			YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
			if (args.length == 0)
			{
				sender.sendMessage("開啟技能面板");
				Inventory i = Bukkit.createInventory(null, 54, ChatColor.GREEN + "技能面板");
				List<String> allSkillFolderName = Skill.getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));
				for (String skillFolderName : allSkillFolderName)
				{
					ItemStack is = new ItemStack(Material.BOOK);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(skillFolderName);
					is.setItemMeta(im);
					i.addItem(is);
				}
				p.openInventory(i);
			}
			else
			if (args.length == 1)
			{
				List<String> allSkillFolderName = Skill.getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));
				for (String skillFolderName : allSkillFolderName)
				{
					if (args[0].equals(skillFolderName))
					{
						Inventory i = Bukkit.createInventory(null, 54, ChatColor.GREEN + "技能面板 " + skillFolderName);
						List<String> allSkillName = Skill.getAllSkillName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
						for (String skillName : allSkillName)
						{
							Skill skill = Skill.getSkill(Skill.getAllSkill(),skillName);
							ItemStack is = new ItemStack(Material.getMaterial(skill.getIcon()));
							ItemMeta im = is.getItemMeta();
							im.setDisplayName(skill.getName());
							if (y.getBoolean(skill.getName() + ".learn"))
							{
								im.setLore(Skill.getLore(skill, y.getInt(skill.getName() + ".level")));
							}
							else
							{
								im.setLore(Skill.getLore(skill, 0));
							}
							is.setItemMeta(im);
							i.setItem(skill.getIndex(), is);
						}
						p.openInventory(i);
					}
				}
			}
			else
			if (args.length == 2 && args[0].equalsIgnoreCase("learn"))
			{
				Skill skill = Skill.getSkill(Skill.getAllSkill(), args[1]);
				try
				{
					if (p.getLevel() >= skill.getSkillReqLevel())
					{
						if (!skill.getSkillReq().equals("無"))
						{
							if (y.getBoolean(skill.getSkillReq() + ".learn"))
							{
								if (skill.isNeedsPer() && p.hasPermission(skill.getPer()))
								{
									if (y.getInt(skill.getName() + ".level") >= skill.getMaxLevel())
									{
										p.sendMessage(ChatColor.RED + "技能等級已滿，不可學習!");
									}
									else
									{
										if (y.getBoolean(skill.getName() + ".learn"))
										{
											y.set(skill.getName() + ".level", y.getInt(skill.getName() + ".level") + 1 );
											y.save(f);
										}
										else
										{
											y.set(skill.getName() + ".learn", true);
											y.set(skill.getName() + ".level", 1);
											y.save(f);
										}
									}
								}
								else
								if (!skill.isNeedsPer())
								{
									if (y.getInt(skill.getName() + ".level") >= skill.getMaxLevel())
									{
										p.sendMessage(ChatColor.RED + "技能等級已滿，不可學習!");
									}
									else
									{
										if (y.getBoolean(skill.getName() + ".learn"))
										{
											y.set(skill.getName() + ".level", y.getInt(skill.getName() + ".level") + 1 );
											y.save(f);
										}
										else
										{
											y.set(skill.getName() + ".learn", true);
											y.set(skill.getName() + ".level", 1);
											y.save(f);
										}
									}
								}
							}
						}
						else
						{
							if (skill.isNeedsPer() && p.hasPermission(skill.getPer()))
							{
								if (y.getInt(skill.getName() + ".level") >= skill.getMaxLevel())
								{
									p.sendMessage(ChatColor.RED + "技能等級已滿，不可學習!");
								}
								else
								{
									if (y.getBoolean(skill.getName() + ".learn"))
									{
										y.set(skill.getName() + ".level", y.getInt(skill.getName() + ".level") + 1 );
										y.save(f);
									}
									else
									{
										y.set(skill.getName() + ".learn", true);
										y.set(skill.getName() + ".level", 1);
										y.save(f);
									}
								}
							}
							else
							if (!skill.isNeedsPer())
							{
								if (y.getInt(skill.getName() + ".level") >= skill.getMaxLevel())
								{
									p.sendMessage(ChatColor.RED + "技能等級已滿，不可學習!");
								}
								else
								{
									if (y.getBoolean(skill.getName() + ".learn"))
									{
										y.set(skill.getName() + ".level", y.getInt(skill.getName() + ".level") + 1 );
										y.save(f);
									}
									else
									{
										y.set(skill.getName() + ".learn", true);
										y.set(skill.getName() + ".level", 1);
										y.save(f);
									}
								}
							}
						}
					}
					else
					{
						p.sendMessage(ChatColor.RED + "等級不足，不可學習!");
					}
				}
				catch (IOException e)
				{
					pr.getLogger().warning(e.toString());
				}
			}
		}
		return false;
	}
	
	
}
