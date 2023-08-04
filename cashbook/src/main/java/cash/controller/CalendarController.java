package cash.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// sesiion 인증 검사
	HttpSession session = request.getSession();
	if(session.getAttribute("loginMember") == null) {
		response.sendRedirect(request.getContextPath()+"/login");
		return;
	}
	
	Member member = (Member)(session.getAttribute("loginMember"));
		
	// view에 넘겨줄 달력정보(모델값)
	Calendar firstDay = Calendar.getInstance(); // 오늘날짜
	  
	// 출력하고자 하는 년도, 월의 기본값은 이번달 1일 
	firstDay.set(Calendar.DATE, 1);
	
	// 출력하고하는 년도와 월이 매개값이 넘어왔다면
	if(request.getParameter("targetYear") != null
			&& request.getParameter("targetMonth") != null) {
		firstDay.set(Calendar.YEAR, Integer.parseInt(request.getParameter("targetYear")));
		// API에서 자동으로 targetMonth 12가 입력되면 월1, 년 +1
		// API에서 자동으로 targetMonth -1이 입력되면 월12, 년 -1
		firstDay.set(Calendar.MONTH, Integer.parseInt(request.getParameter("targetMonth")));
	}
	
	int targetYear = firstDay.get(Calendar.YEAR);
	int targetMonth = firstDay.get(Calendar.MONTH);
	
	// 달력출력시 시작공백 -> 1일날짜의 요일(일1, 월2, ...토6) - 1
	int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK) -1;
	System.out.println(beginBlank + " <-- beginBlank");
	
	// 출력되는 월의 마지막날짜
	int lastDate = firstDay.getActualMaximum(Calendar.DATE);
	System.out.println(lastDate + " <-- lastDate");
	
	// 달력출력시 마지막 날짜 출력후 공백 갯수 -> 전체 출력 세르이 개수가 7로 나누어 떨어져야 한다
	int endBlank = 0;
	if(((beginBlank + lastDate) % 7) != 0) {
		endBlank = 7 - ((beginBlank + lastDate) % 7);
	}
	int totalCell = beginBlank + lastDate + endBlank;
	System.out.println(totalCell + " <-- totalCell");
	System.out.println(endBlank + " <-- endBlank");
	
	// 모델 호출(타겟 월의 수입/지출 데이터)
	List<Cashbook> list
		= new CashbookDao().selsectCashbookListByMonth(member.getMemberId(), targetYear, targetMonth+1);
		
	List<Map<String,Object>> htList
		= new HashtagDao().selectWordCountByMonth(member.getMemberId(), targetYear, targetMonth+1);
	System.out.println(htList.size());
	
	// 뷰에 값 넘기기(request)
	request.setAttribute("targetYear", targetYear);
	request.setAttribute("targetMonth", targetMonth);
	request.setAttribute("lastDate", lastDate);
	request.setAttribute("beginBlank", beginBlank);
	request.setAttribute("totalCell", totalCell);
	request.setAttribute("endBlank", endBlank);
	
	request.setAttribute("list", list);
	request.setAttribute("htList", htList);
		
	// 달력을 출력하는 뷰 
	request.getRequestDispatcher("/WEB-INF/view/calendar.jsp").forward(request, response);
	}
	
}
