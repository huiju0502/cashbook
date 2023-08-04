package cash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/cashbook")
public class CashbookController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		Member member = (Member)(session.getAttribute("loginMember"));
		
		// view에 넘겨줄 달력정보(모델값)
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		// 모델 호출(타겟 년/월/일의 수입/지출 데이터)
		List<Cashbook> list = new CashbookDao().selsectCashbookListByDate(member.getMemberId(), targetYear, targetMonth+1, targetDate);
		
		// 뷰에 값 넘기기(request)
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		request.setAttribute("list", list);
		
		// 해당 년/월/일의 목록 출력
		request.getRequestDispatcher("/WEB-INF/view/cashbook.jsp").forward(request, response);
	}

}