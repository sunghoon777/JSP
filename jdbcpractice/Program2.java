package jdbcpractice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb2";
		String sql = "INSERT INTO NOTICE(TITLE,WRITER_ID,CONTENT,FILES) VALUES(?,?,?,?)";
		int number = 3;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		for(;number<11;number++) {
			st.setString(1, "테스트 제목"+number);
			st.setString(2, "test"+number);
			st.setString(3, "테스트 내용"+number);
			st.setString(4, "테프스 파일"+number);
			st.executeUpdate();
		}
		st.close();
		con.close();
	}

}
