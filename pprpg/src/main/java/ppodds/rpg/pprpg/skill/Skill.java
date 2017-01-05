package ppodds.rpg.pprpg.skill;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ppodds.rpg.pprpg.PPRPG;

public class Skill
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	private String name;
	private String type;
	private int maxLevel;
	private String skillReq;
	private int skillReqLevel;
	private boolean needsPer;
	private String per;
	private String msg;
	private int index;
	private String icon;
	private List<String> iconLore;
	private int effectBase;
	private int nextLevelEffect;
	private int manaBase;
	private int nextLevelMana;
	private int cooldownBase;
	private int nextLevelCooldown;
	private int durationBase;
	private int nextLevelDuration;
	
	private Skill(String name,String type,int maxLevel,String skillReq,int skillReqLevel,boolean needsPer,String per,String msg,int index,String icon,List<String> iconLore,int effectBase,int nextLevelEffect,int manaBase,int nextLevelMana,int cooldownBase,int nextLevelCooldown,int durationBase,int nextLevelDuration)
	{
		this.name = name;
		this.type = type;
		this.maxLevel = maxLevel;
		this.skillReq = skillReq;
		this.skillReqLevel = skillReqLevel;
		this.needsPer = needsPer;
		this.per = per;
		this.msg = msg;
		this.index = index;
		this.icon = icon;
		this.iconLore = iconLore;
		this.effectBase = effectBase;
		this.nextLevelEffect = nextLevelEffect;
		this.manaBase = manaBase;
		this.nextLevelMana = nextLevelMana;
		this.cooldownBase = cooldownBase;
		this.nextLevelCooldown = nextLevelCooldown;
		this.durationBase = durationBase;
		this.nextLevelCooldown = nextLevelDuration;
	}

	public static ArrayList<Skill> getAllSkill()
	{
		List<String> allSkillFolderName = getAllSkillFolderName(new File(pr.getDataFolder() + File.separator + "Skill"));
		ArrayList<Skill> allSkill = new ArrayList<Skill>();
		
		for (String skillFolderName : allSkillFolderName)
		{
			List<String> allSkillName = Skill.getAllSkillFileName(new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName));
			for (String skillName : allSkillName)
			{
				
				File skill = new File(pr.getDataFolder() + File.separator + "Skill" + File.separator + skillFolderName + File.separator + skillName);
				YamlConfiguration data = YamlConfiguration.loadConfiguration(skill);

				String name = data.getString("name");
				String type = data.getString("type");
				int maxLevel = data.getInt("max-level");
				String skillReq = data.getString("skill-req");
				int skillReqLevel = data.getInt("skill-req-lvl");
				boolean needsPer = data.getBoolean("needs-permission");
				String per = data.getString("permission");
				String msg = data.getString("msg");
				int index = data.getInt("index");
				String icon = data.getString("icon");
				List<String> iconLore = data.getStringList("icon-lore");
				int effectBase = data.getInt("attributes.effect-base");
				int nextLevelEffect = data.getInt("attributes.effect-scale");
				int manaBase = data.getInt("attributes.effect-base");
				int nextLevelMana = data.getInt("attributes.mana-scale");
				int cooldownBase = data.getInt("attributes.cooldown-base");
				int nextLevelCooldown = data.getInt("attributes.cooldown-scale");
				int durationBase = data.getInt("attributes.duration-base");
				int nextLevelDuration = data.getInt("attributes.duration-scale");

				Skill skillO = new Skill(name, type, maxLevel, skillReq, skillReqLevel, needsPer, per, msg, index, icon, iconLore, effectBase, nextLevelEffect, manaBase, nextLevelMana, cooldownBase, nextLevelCooldown,durationBase,nextLevelDuration);
				
				skillO.setSkillReq(Skill.checkSkillReq(skillO));
				allSkill.add(skillO);
				
			}
		}
		return allSkill;
	}
	public static Skill getSkill(List<Skill> allSkill,String skillName)
	{

		for (Skill skill : allSkill)
		{
			if (skill.name.equals(skillName))
			{
				return skill;
			}
	  	}
		return null;
		
	}
	public static List<String> getAllSkillFileName(File skillFolder)
	{
		String[] allSkillFileName = skillFolder.list();
		
		List<String> list = new ArrayList<String>();
		for (String skillFileName : allSkillFileName)
		{	
			list.add(skillFileName);
		}
		return list;
	}
	public static List<String> getAllSkillName(File skillFolder)
	{
		String[] allSkillFileName = skillFolder.list();
		
		List<String> list = new ArrayList<String>();
		for (String skillFileName : allSkillFileName)
		{	
			File skillFile = new File(skillFolder.getPath() + File.separator + skillFileName);
			YamlConfiguration data = YamlConfiguration.loadConfiguration(skillFile);
			list.add(data.getString("name"));
		}
		return list;
	}
	public static List<String> getAllSkillFolderName(File skillFolder)
	{
		List<String> allSkillFolderName = new ArrayList<String>();
		for (File f : skillFolder.listFiles())
		{
			if (f.isDirectory())
			{
				allSkillFolderName.add(f.getName());
			}
		}
		return allSkillFolderName;
	}
	public static List<String> getLore(Skill skill,int skillLevel)
	{
		List<String> list = new ArrayList<String>();
		for (String str : skill.iconLore)
		{
			list.add(str.replace("{name}",skill.name).replace("{level}", String.valueOf(skillLevel)).replace("{maxLevel}", String.valueOf(skill.maxLevel)).replace("{type}", skill.type).replace("{effect}", String.valueOf(skill.effectBase +  skillLevel * skill.nextLevelEffect)).replace("{nextLevelEffect}", String.valueOf(skill.nextLevelEffect)).replace("{mana}", String.valueOf(skill.manaBase + skillLevel * skill.nextLevelMana)).replace("{nextLevelMana}", String.valueOf(skill.nextLevelMana)).replace("{cooldown}", String.valueOf(skill.cooldownBase + skillLevel * skill.nextLevelCooldown)).replace("{nextLevelCooldown}", String.valueOf(skill.nextLevelCooldown)).replace("{skillReq}", skill.skillReq).replace("{skillReqLevel}", String.valueOf(skill.skillReqLevel)).replace("{duration}", String.valueOf((skill.durationBase + skillLevel * skill.nextLevelDuration) / 20)).replace("{nextLevelDuration}", String.valueOf(skill.nextLevelDuration)));
		}
		return list;
	}
	private static String checkSkillReq(Skill skill)
	{
		if (skill.getSkillReq().equals(""))
		{
			return "無";
		}
		return skill.getSkillReq();
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public int getMaxLevel()
	{
		return maxLevel;
	}

	public String getSkillReq()
	{
		return skillReq;
	}

	public int getSkillReqLevel()
	{
		return skillReqLevel;
	}

	public boolean isNeedsPer()
	{
		return needsPer;
	}

	public String getPer()
	{
		return per;
	}

	public String getMsg()
	{
		return msg;
	}

	public int getIndex()
	{
		return index;
	}

	public String getIcon()
	{
		return icon;
	}

	public List<String> getIconLore()
	{
		return iconLore;
	}

	public int getEffectBase()
	{
		return effectBase;
	}

	public int getNextLevelEffect()
	{
		return nextLevelEffect;
	}

	public int getManaBase()
	{
		return manaBase;
	}

	public int getNextLevelMana()
	{
		return nextLevelMana;
	}

	public int getCooldownBase()
	{
		return cooldownBase;
	}

	public int getNextLevelCooldown()
	{
		return nextLevelCooldown;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	public void setSkillReq(String skillReq)
	{
		this.skillReq = skillReq;
	}

	public void setSkillReqLevel(int skillReqLevel)
	{
		this.skillReqLevel = skillReqLevel;
	}

	public void setNeedsPer(boolean needsPer)
	{
		this.needsPer = needsPer;
	}

	public void setPer(String per)
	{
		this.per = per;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public void setIconLore(List<String> iconLore)
	{
		this.iconLore = iconLore;
	}

	public void setEffectBase(int effectBase)
	{
		this.effectBase = effectBase;
	}

	public void setNextLevelEffect(int nextLevelEffect)
	{
		this.nextLevelEffect = nextLevelEffect;
	}

	public void setManaBase(int manaBase)
	{
		this.manaBase = manaBase;
	}

	public void setNextLevelMana(int nextLevelMana)
	{
		this.nextLevelMana = nextLevelMana;
	}

	public void setCooldownBase(int cooldownBase)
	{
		this.cooldownBase = cooldownBase;
	}

	public void setNextLevelCooldown(int nextLevelCooldown)
	{
		this.nextLevelCooldown = nextLevelCooldown;
	}

	public int getDurationBase() {
		return durationBase;
	}

	public int getNextLevelDuration() {
		return nextLevelDuration;
	}

	public void setDurationBase(int durationBase) {
		this.durationBase = durationBase;
	}

	public void setNextLevelDuration(int nextLevelDuration) {
		this.nextLevelDuration = nextLevelDuration;
	}

}
