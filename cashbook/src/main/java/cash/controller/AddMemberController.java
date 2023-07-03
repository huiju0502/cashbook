package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/addMember")
public class AddMemberController extends HttpServlet {

	// addMember.jsp 회원가입폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사(null 일때)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			// jsp 페이지로 포워드(디스패치)
			request.getRequestDispatcher("/WEB-INF/view/addMember.jsp").forward(request, response);
		}
		response.sendRedirect(request.getContextPath()+"/cashbook");
	}
	// 회원가입 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사(null 일때)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			// request.getParameter()
			String memberId = request.getParameter("memberId");
			String memberPw = request.getParameter("memberPw");
			Member member = new Member(memberId, memberPw, null, null);
			System.out.println(memberId);
			// 회원가입 DAO 호출
			MemberDao memberDao = new MemberDao();
			int row = memberDao.insertMember(member);
			System.out.println(row);
			if(row == 0 ) { // 회원가입 실패시
				// addMember.jsp view를 이동하는 controller를 리다이렉트
				response.sendRedirect(request.getContextPath()+"/addMember");
			} else if(row ==1) { // 회원가입 성공시
				// login.jsp view를 이동하는 controller를 리다이렉트
				response.sendRedirect(request.getContextPath()+"/login");
			} else {
				System.out.println("add member error");
			}
			
		}
	
	}
}