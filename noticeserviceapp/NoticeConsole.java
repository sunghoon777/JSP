package noticeserviceapp;

import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class NoticeConsole {

	NoticeService noticeService = new NoticeService();
	private int page = 1;
	private Scanner scan = new Scanner(System.in);
	int count = 0; // 총 개수

	// Notice 상세 내용 출력
	public void printNotice() throws ClassNotFoundException, SQLException {
		System.out.println("조회 할 공지사항 번호를 입력하세요");
		int id = scan.nextInt();
		scan.nextLine();
		Notice notice = noticeService.getNoticeRecord(id);
		System.out.println(notice.getId() + "  " + notice.getTitle() + "  " + notice.getWriterId() + "  "
				+ notice.getRegDate() + "  " + notice.getContent() + "  " + notice.getHit() + "  " + notice.getFiles());
	}

	// Notice 리스트 출력(page 크기로)
	public void printNoticeList() throws ClassNotFoundException, SQLException {
		List<Notice> list = noticeService.getList(page);
		count = noticeService.getCount();
		System.out.println("<공지사항> 총 " + count + "개 게시글");
		System.out.println("___________________________________________");
		for (Notice notice : list) {
			System.out.println(notice);
		}
		System.out.println("___________________________________________");
		int endPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
		System.out.println(page + " / " + endPage + " pages");
	}
	

	// 이전 page 출력
	public void printPreviousNoticeList() throws ClassNotFoundException, SQLException {
		if (page != 1) {
			page--;
			printNoticeList();
		} else {
			System.out.println("처음 페이지 입니다.");
		}
	}

	// 다음 페이지 출력
	public void printNextNoticeList() throws ClassNotFoundException, SQLException {
		count = noticeService.getCount();
		int endPage = count % 10 == 0 ? count / 10 : count / 10 + 1;
		page++;
		// 페이지가 마지막 페이지보다 작거나 같을때에만 다음 페이지를 출력할수있다.
		if (page <= endPage) {
			printNoticeList();
		}
		// 다음 페이지로 넘어가면 마지막 페이지보다 커지므로 다시 페이지를 뺴준다.
		else {
			page--;
			System.out.println("마지막 페이지 입니다.");
		}
	}

	// Notice 작성
	public void inputNotice() throws ClassNotFoundException, SQLException {
		System.out.print("제목 작성자id 내용 파일순으로 입력>>");
		String title = scan.next();
		String writerId = scan.next();
		String content = scan.next();
		String files = scan.next();
		noticeService.insert(new Notice(0, title, writerId, new Date(0), content, 0, files));
	}
	
	//Notice 검색
	public void searchNotice() throws ClassNotFoundException, SQLException {
		System.out.print("검색 범주 1. TITLE 2. CONTENT 3. WRITERID 중에 하나를 입력하세요 >> ");
		int select = scan.nextInt();
		scan.nextLine();
		if(select != 1 && select != 2 && select !=3) {
			System.out.println("잘못입력했어요");
			return;
		}
		System.out.print("검색어를 입력하세요 >> ");
		String searchWord = scan.nextLine();
		List<Notice> list = noticeService.getSearchList(select, searchWord);
		System.out.println("검색 결과 총"+list.size()+"개");
		System.out.println("___________________________________________");
		for(Notice notice : list) {
			System.out.println(notice);
		}
		System.out.println("___________________________________________");
	}

	// 메뉴 선택 CLI
	public void inputNoticeMenu() throws ClassNotFoundException, SQLException {
		while (true) {
			System.out.print("1. 상세조회 2. 이전 3. 다음 4. 글쓰기 5. 그만 6. 검색 >> ");
			int select = scan.nextInt();
			scan.nextLine();
			if (select == 1) {
				printNotice();
			} else if (select == 2) {
				printPreviousNoticeList();
			} else if (select == 3) {
				printNextNoticeList();
			} else if (select == 4) {
				inputNotice();
			} else if (select == 5) {
				scan.close();
				return;
			} else if (select == 6) {
				searchNotice();
			} else {
				System.out.println("잘못입력했어요.");
			}
		}
	}

}
