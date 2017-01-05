package ppodds.rpg.pprpg.skill.mana;

import org.bukkit.entity.Player;

public class ManaBar
{
	private Player player;
	private int maxMana;
	private int regenAmount;
	private int mana;
	
	public ManaBar(Player player,int maxMana,int regenAmount)
	{
		this.player = player;
		this.maxMana = maxMana;
		this.regenAmount = regenAmount;
		this.mana = maxMana;
	}

	public Player getPlayer()
	{
		return player;
	}

	public int getMaxMana()
	{
		return maxMana;
	}

	public int getRegenAmount()
	{
		return regenAmount;
	}

	public int getMana()
	{
		return mana;
	}

	public void setMaxMana(int maxMana)
	{
		this.maxMana = maxMana;
	}

	public void setRegenAmount(int regenAmount)
	{
		this.regenAmount = regenAmount;
	}

	public boolean setMana(int mana)
	{
		int newMana = mana;
		if (newMana > maxMana)
		{
			newMana = maxMana;
		}
		else
		if (newMana < 0)
		{
			newMana = 0;
		}
		
		if (newMana == mana)
		{
			return false;
		}
		mana = newMana;
		return true;
	}
	
	public boolean changeMana(int amount)
	{
		int newMana = this.mana;
		
		if (amount > 0)
		{
			if (this.mana == this.maxMana)
			{
				return false;
			}
			newMana += amount;
			if (newMana > maxMana)
			{
				newMana = maxMana;
			}
		}
		else
		if (amount < 0)
		{
			if (mana == 0)
			{
				return false;
			}
			newMana += amount;
			if (newMana < 0)
			{
				newMana = 0;
			}
		}
		if (newMana == mana)
		{
			return false;
		}

		if (newMana > maxMana)
		{
			newMana = maxMana;
		}
		if (newMana < 0)
		{
			newMana = 0;
		}
		if (newMana == mana)
		{
			return false;
		}
		mana = newMana;
		return true;
	}
	
	public boolean regenerate()
	{
		if ((regenAmount > 0 && mana == maxMana) || (regenAmount < 0 && mana == 0))
		{
			return false;
		}
		return changeMana(regenAmount);
	}
	
}
