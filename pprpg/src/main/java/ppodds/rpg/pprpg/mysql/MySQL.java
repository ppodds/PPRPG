package ppodds.rpg.pprpg.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import ppodds.rpg.pprpg.PPRPG;

public class MySQL
{
	private final static PPRPG pr = (PPRPG) Bukkit.getPluginManager().getPlugin("PPRPG");
	
	public static Connection con()
	{
		try
		{
			String port = pr.getConfig().getString("MySQL.SQLport");
	    	String Database = pr.getConfig().getString("MySQL.SQLDatabase");
	    	String Account = pr.getConfig().getString("MySQL.UseAccount");
	    	String Password = pr.getConfig().getString("MySQL.UsePassword");
	    	
	        String conUrl = "jdbc:mysql://localhost:" + port + "/" + Database ;
	        
	        try
	        {
	            Class.forName("com.mysql.jdbc.Driver");
	            return DriverManager.getConnection(conUrl,Account,Password);
	        }
	        catch(ClassNotFoundException e) 
	        { 
	        	pr.getLogger().warning("DriverClassNotFound :" + e.toString()); 
	        }
	        catch(SQLException e)
	        { 
	        	pr.getLogger().warning("Exception :"+ e.toString());
	        }
		}
		catch (Exception e)
		{
			pr.getLogger().warning(e.toString());
		}
		return null;
	}
	public static void buildSQL()
	{
		PlayerDB.buildPlayerDB();
	}
}
