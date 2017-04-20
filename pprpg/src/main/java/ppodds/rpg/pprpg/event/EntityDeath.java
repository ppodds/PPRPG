package ppodds.rpg.pprpg.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import ppodds.rpg.pprpg.PPRPG;

public class EntityDeath implements Listener
{
	private static final PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e)
	{
		if (e.getEntity() instanceof Monster && e.getEntity().getKiller() != null)
		{
			Monster monster = (Monster) e.getEntity();
			Player p = e.getEntity().getKiller();
			int level = p.getLevel();
			String monsterName = monster.getCustomName().substring(0,monster.getCustomName().indexOf(" "));
			int monsterLevel = Integer.parseInt((monster.getCustomName().substring(monster.getCustomName().lastIndexOf(" "))).trim());
			ppodds.rpg.pprpg.monster.Monster monsterData = ppodds.rpg.pprpg.monster.Monster.getMonster(monsterName);
			
			if ((monsterLevel - level)<0)
			{
				double d = 1 / Math.abs(monsterLevel - level);
				e.setDroppedExp((int) (monsterData.getExp() + monsterData.getExpScale() * d));
			}
			else
			{
				e.setDroppedExp(monsterData.getExp() + monsterData.getExpScale() * (monsterLevel - level));
			}
			
		}
	}
}
