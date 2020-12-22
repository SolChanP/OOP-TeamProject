package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public DBManager() {
		
	}
	public Connection makeDB() {
		String url = "jdbc:mysql://localhost:3306";
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("데이터베이스 연결중");
			con=DriverManager.getConnection(url, "root", "pbs487900!");
			System.out.println("데이터베이스 연결 성공");
			
		}catch(ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}catch(SQLException ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}
		
		return con;
	}

}
