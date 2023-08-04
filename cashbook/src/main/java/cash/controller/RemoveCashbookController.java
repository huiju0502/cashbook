package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;

@WebServlet("/removeCashbook")
public class RemoveCashbookController extends HttpServlet {
	
	// 삭제액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		//CashbookDao cashbookDao = new CashbookDao();
		//int remove = cashbookDao.deleteCashbook(null);
		
		//Member loginMember = (Member)session.getAttribute("loginMember");
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));		
		
		CashbookDao cashbookDao = new CashbookDao();
		int remove = cashbookDao.deleteCashbook(cashbookNo);
			System.out.println(remove + "<-내역 삭제");
		if(remove == 1) {// 성공
			System.out.println("삭제 성공");
			System.out.println(cashbookNo);
			response.sendRedirect(request.getContextPath() + "/cashbook?targetYear="+ targetYear + "&targetMonth=" + targetMonth + "&targetDate=" + targetDate);
		} else{ // 실패
			System.out.println("삭제 실패");
			System.out.println(cashbookNo);
			response.sendRedirect(request.getContextPath() + "/cashbook?targetYear="+ targetYear + "&targetMonth=" + targetMonth + "&targetDate=" + targetDate);
			return;
		} 
	}

}
