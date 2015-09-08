package me.Mark.HG.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MySQL {

	private static Connection con;

	private static String table;

	public static void openConnection(String hostname, int port, String database, String table, String user,
			String password) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, user, password);
		MySQL.table = table;

		PreparedStatement ss = con.prepareStatement("SHOW TABLES");
		ResultSet r = ss.executeQuery();
		if (!r.next()) {
			PreparedStatement s = con.prepareStatement("CREATE TABLE `hg` (`uuid` varchar(36) NOT NULL,"
					+ "`name` varchar(16) NOT NULL,`kills` int(11) NOT NULL,"
					+ "`deaths` int(11) NOT NULL,`wins` int(11) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8");
			s.execute();
		}
	}

	public static void closeConnection() {
		try {
			if (con != null && !con.isClosed())
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public static synchronized void incrementStat(UUID id, String statname) throws SQLException {
		checkDataContainsPlayer(id);
		PreparedStatement s = con
				.prepareStatement("UPDATE `" + table + "` SET " + statname + "=" + statname + "+1 WHERE uuid=?;");
		s.setString(1, id.toString());
		s.execute();
	}

	private synchronized static void checkDataContainsPlayer(UUID id) throws SQLException {
		PreparedStatement st = con.prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?;");
		st.setString(1, id.toString());
		ResultSet r = st.executeQuery();
		if (!r.next())
			insertNewPlayer(id);
	}

	// uuid, lastname, deaths, kills, wins

	private synchronized static void insertNewPlayer(UUID id) throws SQLException {
		PreparedStatement s = con.prepareStatement("INSERT INTO `" + table + "` values(?, ?, 0, 0, 0);");
		s.setString(1, id.toString());
		Player p = Bukkit.getPlayer(id);
		if (p != null)
			s.setString(2, p.getName());
		else
			s.setString(2, "unknown");
		s.execute();
	}

	@Deprecated
	public static synchronized void updateName(UUID id, String name) throws SQLException {
		PreparedStatement s = con.prepareStatement("UPDATE `" + table + "` SET name=? WHERE uuid=?;");
		s.setString(1, name);
		s.setString(2, id.toString());
		s.execute();
	}
}
