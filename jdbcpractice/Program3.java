package jdbcpractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program3 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb2";
		String sql = "UPDATE NOTICE SET FILES=? WHERE ID = ?";
		int number = 3;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		for(;number<11;number++) {
			st.setString(1, "테스트 파일"+number);
			st.setInt(2, number);
			st.executeUpdate();
		}
		st.close();
		con.close();
	}

}
