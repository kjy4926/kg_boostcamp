package project01_survey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
	private static JdbcConnector instance = new JdbcConnector();
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String id = "c##test";
	private static String pwd = "test";
	
	private JdbcConnector() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JdbcConnector getInstance() {
		return instance;
	}
	
	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url, id, pwd);
		return conn;
	}
}
