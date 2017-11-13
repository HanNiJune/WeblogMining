package research.weblog;
import java.sql.*;
import java.util.Date;

public class DBConnector {

	public static String fileName = "WebLog.mdb";
		
	public static Connection Connect () {
		Connection conn = null;		
		try {			
			String url = "jdbc:odbc:;DBQ="+fileName+";DRIVER={Microsoft Access Driver (*.mdb)}";
      		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			conn = DriverManager.getConnection(url);
		}
		catch(ClassNotFoundException e) {
			System.out.println("Conncet Error::" + e.getMessage());
			
		}
		catch(SQLException e) {
			System.out.println("Connect Error::" + e.getMessage());
		}
		return conn;
	}
	
	public static void Disconnect (Connection cn) {		
		try {
			if (!cn.isClosed())
			cn.close();
		}
		catch (SQLException e) {
			System.out.println("Disconnect Error::"+ e.getMessage());
		}
	}	
	
	public static boolean ExecuteStatement (String strSql, Connection cn) {
		try {
			Statement stmt = cn.createStatement();
			stmt.execute(strSql);
			//stmt.executeUpdate(strSql);
		}
		catch(SQLException e) {
			System.out.print("ExecuteStatement Error::"+e.getMessage());
			System.out.println(strSql);
			return false;
		}			
		
		return true;
	}
	
	public static ResultSet GetQuery(String strSql, Connection cn) {
		ResultSet rs = null;
		
		try {
			
			Statement stmt = cn.createStatement();
			
			rs = stmt.executeQuery(strSql);
			
		}
		catch (SQLException e) {
			System.out.print("GetQuery Error::"+e.getMessage());
			System.out.println(strSql);
		}
		return rs;
	}
	
	public static int GetNewID(String tblName, String colName, Connection cn) {
		String strSql;
		ResultSet rs = null;
		int iID = 0;
		
		try {
			Statement stmt = cn.createStatement();
			strSql = "SELECT MAX (" + colName + ") AS MID FROM " + tblName;
			
			rs = stmt.executeQuery(strSql);
			
			if (rs.next()) {
				iID = rs.getInt("MID") + 1;
			}
		}
		catch(SQLException e) {
			System.out.println("GetNewID::"+ e.getMessage());
		}
		
		return iID;
	}
}	