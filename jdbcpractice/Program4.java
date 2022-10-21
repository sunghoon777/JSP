package jdbcpractice;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program4 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb2";
		String sql = "DELETE NOTICE";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		st.execute();
		st.close();
		con.close();
	}

}
