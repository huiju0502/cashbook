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

@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {
	// 비밀번호 수정폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/modifyMember.jsp").forward(request, response);
	}

	// 수정
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberPw = request.getParameter("memberPw");
		
		Member member = new Member(loginMember.getMemberId(), memberPw, null, null);
		
		MemberDao memberDao = new MemberDao();
		int modify = memberDao.updateMember(member);
		
		if(modify == 0) { // 수정 실패
			System.out.println("수정 실패");
			response.sendRedirect(request.getContextPath() + "/modifyMember");
			return;
		}
		// 수정 성공
		System.out.println("수정 성공");
		response.sendRedirect(request.getContextPath()+"/memberOne");
		
	}

}
