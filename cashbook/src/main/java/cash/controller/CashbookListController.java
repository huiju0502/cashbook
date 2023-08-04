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

@WebServlet("/cashbookListByTag")
public class CashbookListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		Member member = (Member)(session.getAttribute("loginMember"));
		String word = request.getParameter("word");
		
		CashbookDao cashbookDao = new CashbookDao();
		// 페이징
		// 현재 페이지 
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
		// 전체행의 수
		int totalRow = cashbookDao.selectCashbookListCnt(member.getMemberId(), word);
		// 페이지당 행의 수 
		int rowPerPage = 10;
		// 시작행 번호
		int beginRow = (currentPage-1) * rowPerPage;
		int endRow = beginRow + (rowPerPage - 1);
		if(endRow > totalRow) {
				endRow = totalRow;
		}
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage = lastPage + 1;
		}
		
		// 페이지 네비게이션 페이징
		int pagePerPage = 10;
		int minPage = (((currentPage-1) / pagePerPage) * pagePerPage) + 1;
		int maxPage = minPage + (pagePerPage - 1);
		if(maxPage > lastPage) {
			maxPage = lastPage;
		}
		
		// 모델 호출(해당 해시태그별 리스트)
		List<Cashbook> list = cashbookDao.selectCashbookByTag(
				member.getMemberId(), word, beginRow, rowPerPage);
		
		// 뷰에 값 넘기기(request)
		request.setAttribute("minPage", minPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagePerPage", pagePerPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("word", word);
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
	}

}
