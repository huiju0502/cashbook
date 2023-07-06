package cash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 인증 검사 코드
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/calendar");
		}
		
		// 쿠키에 저장된 아이디가 있다면 request 속성에 저장
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("loginId") == true) {
					request.setAttribute("loginId", c.getValue());
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		Member member = new Member(memberId, memberPw, null, null);
		
		MemberDao memberDao = new MemberDao();
		Member loginMember = memberDao.selectMemberById(member);
		
		// 로그인 실패시
		if(loginMember == null) {
			System.out.println("로그인 실패");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		} 
		
		// 로그인 성공시
		// session에 저장
		HttpSession session = request.getSession();
		System.out.println("로그인 성공");
		session.setAttribute("loginMember", loginMember);
		// 쿠키에 저장
		if(request.getParameter("idSave") != null) {
			Cookie loginIdCookie =  new Cookie("loginId", loginMember.getMemberId());
			response.addCookie(loginIdCookie);
		}
		response.sendRedirect(request.getContextPath()+"/calendar");
		
	}

}
