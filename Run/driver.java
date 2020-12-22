package Run;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import DB.DBManager;
import GUI.MainFrame;


public class driver {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//DB관리자 및 커넥터 생성
		DBManager db = new DBManager();
		Connection con = db.makeDB();
		Statement stmt = con.createStatement();
		
		//GUI 생성
		new MainFrame(stmt);
	}
}
