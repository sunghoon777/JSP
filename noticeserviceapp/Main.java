package noticeserviceapp;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		NoticeConsole console = new NoticeConsole();
		console.printNoticeList();
		console.inputNoticeMenu();
	}

}
