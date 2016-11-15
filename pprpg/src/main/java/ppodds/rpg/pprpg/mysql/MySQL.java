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
		try
		{
			Connection con = MySQL.con();
			pr.getLogger().info("資料庫連線成功!");
			Statement stmt = con.createStatement();
			DatabaseMetaData dmd = con.getMetaData();
            ResultSet rs = dmd.getTables(null, null, "PlayerStats", null);
            if (!rs.next())
            {
            	pr.getLogger().info("玩家資料庫不存在!建立玩家資料庫中!");
                String sql = "CREATE TABLE PlayerStats (UUID VARCHAR(50), 筋力 INT, 敏捷 INT, 智力 INT, 經驗 INT, 點數 INT)";
                stmt.executeUpdate(sql); 
                pr.getLogger().info("玩家資料庫建立成功!");
            }
            rs.close();
            stmt.close();
            con.close();
		}
		catch (SQLException e)
		{
			pr.getLogger().warning(e.toString());
		}
	}
}
