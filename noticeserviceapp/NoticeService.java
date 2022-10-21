package noticeserviceapp;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.aq.AQNotificationEvent;

public class NoticeService {
	
	private String url = "jdbc:oracle:thin:@localhost:1521/xepdb2";
	
	//page 단위로 Notice 레코드들을 가져오기
	public List<Notice> getList(int page) throws ClassNotFoundException, SQLException{
		String sql = "SELECT * FROM NOTICE_VIEW WHERE ROWNUMBER BETWEEN ? AND ?";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, 10*(page-1)+1);
		st.setInt(2, 10*page);
		ResultSet rs = st.executeQuery();
		List<Notice> list = new ArrayList<Notice>();
		while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regDate = rs.getDate("REGDATE");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			list.add(new Notice(id,title,writerId,regDate,content,hit,files));
		}
		rs.close();
		st.close();
		con.close();
		return list;
	}
	
	//특정 ID를 가지는 Notice 레코드 가져오기
	public Notice getNoticeRecord(int id) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM NOTICE WHERE ID = ?";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regDate = rs.getDate("REGDATE");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			return new Notice(id,title,writerId,regDate,content,hit,files);
		}
		else {
			return new Notice();
		}
	}
	
	//Notice 레코드의 총 개수 얻기
	public int getCount() throws ClassNotFoundException, SQLException {
		String sql = "SELECT COUNT(*) AS COUNT FROM NOTICE_VIEW";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int count = 0;
		if(rs.next()) {
			count = rs.getInt("COUNT");
		}
		return count;
	}
	
	//Notice 레코드 집어넣기
	public int insert(Notice notice) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO NOTICE(TITLE,WRITER_ID,CONTENT,FILES) VALUES(?,?,?,?)";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		int result = 0;
		System.out.println(notice.getTitle()+"  "+notice.getWriterId()+"  "+notice.getContent()+"  "+notice.getFiles()+" 이 입력되었습니다.");
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getWriterId());
		st.setString(3, notice.getContent());
		st.setString(4, notice.getFiles());
		result = st.executeUpdate();
		return result;
	}
	
	//Notice 레코드 내용 변경하기
	public int update(Notice notice) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE NOTICE SET TITLE = ?, CONTENT = ?, FILES = ? WHERE ID = ?";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		int result = 0;
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getContent());
		st.setString(3, notice.getFiles());
		st.setInt(4, notice.getId());
		result = st.executeUpdate();
		return result;
	}
	
	//Notice 레코드 내용 지우기
	public int delete(Notice notice) throws ClassNotFoundException, SQLException {
		String sql = "DELETE NOTICE WHERE ID = ?";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		PreparedStatement st = con.prepareStatement(sql);
		int result = 0;
		st.setInt(1, notice.getId());
		result = st.executeUpdate();
		return result;
	}

	//검색 내용에 해당되는 Notice 레코드들을 가져오기
	public List<Notice> getSearchList(int select, String searchWord) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "SUNGHOON", "1234");
		String sql = "";
		switch (select) {
		case 1:
			sql = "SELECT * FROM NOTICE WHERE TITLE LIKE ? ORDER BY REGDATE DESC";
			break;
		case 2:
			sql = "SELECT * FROM NOTICE WHERE CONTENT LIKE ? ORDER BY REGDATE DESC";
			break;
		case 3:
			sql = "SELECT * FROM NOTICE WHERE WRITER_ID LIKE ? ORDER BY REGDATE DESC";
			break;
		}
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchWord+"%");
		ResultSet rs = st.executeQuery();
		List<Notice> list = new ArrayList<Notice>();
		while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regDate = rs.getDate("REGDATE");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			list.add(new Notice(id,title,writerId,regDate,content,hit,files));
		}
		rs.close();
		st.close();
		con.close();
		return list;
	}
	
	
	
	
}
