package io.essentials.data;

import org.bukkit.Bukkit;
import io.essentials.Essentials;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class MySQL {
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private String sql;

    private Connection createConn(){

        try {

            conn = DriverManager.getConnection("jdbc:mysql://"+
                Essentials.configManager.getConfig().getString("mysql.host") +":"+
                Essentials.configManager.getConfig().getInt("mysql.port")+"/"+
                Essentials.configManager.getConfig().getString("mysql.database")+
                "?autoReconnect=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",

            Essentials.configManager.getConfig().getString("mysql.username"),
                Essentials.configManager.getConfig().getString("mysql.password"));


            if(conn == null && conn.isClosed()){
                Essentials.getInstance().getLogger().log(Level.SEVERE, "Unable to connect to MySQL, Shutting plugin down");
                Bukkit.getPluginManager().disablePlugin(Essentials.getInstance());
            }else{

            }

            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println("Unable to connect to database...");
        System.exit(0);
        return null;
    }

    private Connection getConn(){

        try {
            conn = (conn == null ? createConn() : conn.isClosed() ? createConn() : conn);
            if(conn == null && conn.isClosed()){
                Essentials.getInstance().getLogger().log(Level.SEVERE, "Unable to connect to MySQL, Shutting plugin down");
                Bukkit.getPluginManager().disablePlugin(Essentials.getInstance());

            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setup(){

        sql = "CREATE TABLE IF NOT EXISTS `essentials_Players` (\n" +
                "`UUID` VARCHAR(254) NOT NULL,\n" +
                "`Playtime` BIGINT NOT NULL,\n" +
                "`TpToggle` TINYINT NOT NULL,\n" +
                "`IsFlying` TINYINT NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        execute(sql);

    }


    public void execute(String sql){
        try {
            preparedStatement = getConn().prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql){
        try {
            preparedStatement = getConn().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getPlayer(UUID playerUUID) {
        sql = "SELECT * FROM essentials_Players WHERE UUID = '" + playerUUID + "';";
        return executeQuery(sql);
    }
}