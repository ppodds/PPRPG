package ppodds.rpg.pprpg.spells;

import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.skill.Skill;

public abstract class Spell
{
	//裝飾用的抽象類別 之後可能添加內容
	public abstract boolean cast(Player player,Skill skill);
}
