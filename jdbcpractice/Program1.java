package jdbcpractice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;

public class Program1 {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb2";
		String sql = "SELECT * FROM NOTICE WHERE HIT >= 30";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regDate = rs.getDate("REGDATE");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			System.out.printf("id : %d title : %s writerId ; %s content : %s regDate %s hit : %d files : %s\n"
			,id,title,writerId,content,regDate.toString(),hit,files);
		}
		rs.close();
		st.close();
		con.close();
	}

}
