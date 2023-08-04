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

@WebServlet("/removeMember")
public class RemoveMemberController extends HttpServlet {
	// 비밀번호 입력폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/removeMember.jsp").forward(request, response);
	}
	
	// 탈퇴
	@Override
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
		int remove = memberDao.deleteMember(member);
		
		if(remove == 0) { // 탈퇴실패시
			System.out.println("탈퇴 실패");
			response.sendRedirect(request.getContextPath() + "/removeMember");
			return;
		}
		// 탈퇴 성공
		session.invalidate();
		System.out.println("탈퇴 성공");
		response.sendRedirect(request.getContextPath()+"/login");
	}

}
