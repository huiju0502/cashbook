package cash.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;

@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	// 입력폼 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		//request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		// 나머지 데이터는 입력폼에서 사용자가 입력
		
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		
		request.getRequestDispatcher("/WEB-INF/view/cashbook.jsp").forward(request, response);
	}
	// 입력액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// request 매개값
		// 아이디 : 세션값에서 가져옴
		HttpSession session = request.getSession();
		Member member = (Member)(session.getAttribute("loginMember"));
		String memberId = member.getMemberId();
		// 분류
		String category = request.getParameter("category");
		// 날짜
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		String cashbookDate = targetYear + "-" + (targetMonth >=10 ? targetMonth+1 : "0" + (targetMonth+1))  + "-" + (targetDate >= 10 ? targetDate : "0" + targetDate);
		// 금액
		int price = Integer.parseInt(request.getParameter("price"));
		// 메모
		String memo = request.getParameter("memo");
		
		// 디버깅
		System.out.println(memberId + " <-- memberId");
		System.out.println(category + " <-- category");
		System.out.println(cashbookDate + " <-- cashbookDate");
		System.out.println(price + " <-- price");
		System.out.println(memo + " <-- memo");
		
		// 객체에 저장
		Cashbook cashbook = new Cashbook();
		cashbook.setMemberId(memberId);
		cashbook.setCategory(category);
		cashbook.setCashbookDate(cashbookDate);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		
		
		CashbookDao cashbookDao = new CashbookDao();
		int cashbookNo = cashbookDao.insertCashbook(cashbook); // 키값 반환
		
		// 입력실패시
		if(cashbookNo == 0) {
			System.out.println("입력실패");
			response.sendRedirect(request.getContextPath()+"/cashbook?targetYear=" +targetYear + "&targetMonth=" + targetMonth + "&targetDate=" + targetDate);
			return;
		} 
		// 입력성공시 -> 해시태그 있다면 -> 해시 태그추출 - > 해시태그 입력(반복)
		// 해시 태그 추출 알고리즘
		// # #구디 #구디 #자바
		System.out.println("입력성공");
		System.out.println(cashbookNo + " <-- cashbookNo");
		HashtagDao hashtagDao = new HashtagDao();
		String memo2 = cashbook.getMemo();
		String memo3 = memo.replace("#", " #"); // "#구디#아카데미" -> " #구디 #아케데미"
		
		// 중복된 해시태그방지를 위해 set자료구조를 사용
		Set<String> set = new HashSet<String>(); 
		
		for(String ht : memo3.split(" ")) {
			if(ht.startsWith("#")) {
				String word = ht.replace("#", "");
				if(word.length() > 0) {
					set.add(word);
				}
			}
		}
		for(String s : set) {
			Hashtag hashtag = new Hashtag();
			hashtag.setCashbookNo(cashbookNo);
			hashtag.setWord(s);
			hashtagDao.insertHashtag(hashtag);
		}
		response.sendRedirect(request.getContextPath()+ "/cashbook?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&targetDate=" + targetDate);
	}

}
