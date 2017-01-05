package ppodds.rpg.pprpg.spells;

import org.bukkit.entity.Player;

import ppodds.rpg.pprpg.skill.Skill;

public abstract class Spell
{
	public abstract boolean cast(Player player,Skill skill);
}
