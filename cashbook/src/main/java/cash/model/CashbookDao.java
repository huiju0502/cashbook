package cash.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Statement;

import cash.vo.Cashbook;

public class CashbookDao {
	// 달력에 월별 출력
	public List<Cashbook> selsectCashbookListByMonth(
			String memberId, int targetYear, int targetMonth){
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate FROM cashbook WHERE member_id=? AND YEAR(cashbook_date)=? AND MONTH(cashbook_date)=? ORDER BY cashbook_date ASC";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// 리스트에 일자별 출력
	public List<Cashbook> selsectCashbookListByDate(
			String memberId, int targetYear, int targetMonth, int targetDate){
		List<Cashbook> list = new ArrayList<Cashbook>();
			
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT cashbook_no cashbookNo, category, price, memo, cashbook_date cashbookDate, updatedate, createdate FROM cashbook WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? AND DAY(cashbook_date) = ? ORDER BY createdate ASC";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			stmt.setInt(4, targetDate);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				c.setUpdatedate(rs.getString("updatedate"));
				c.setCreatedate(rs.getString("createdate"));
				list.add(c);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// 가계부 입력
	// 반환값 : cashbook_no
	public int insertCashbook(Cashbook cashbook) {
		int cashbookNo = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO cashbook(member_id, category, cashbook_date, price, memo, updatedate, createdate) VALUES(?, ?, ?, ?, ?, now(), now())";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println(stmt);
			stmt.setString(1, cashbook.getMemberId());
			stmt.setString(2, cashbook.getCategory());
			stmt.setString(3, cashbook.getCashbookDate());
			stmt.setInt(4, cashbook.getPrice());
			stmt.setString(5, cashbook.getMemo());
			int row = stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return cashbookNo;
	}
	
	// 해시태그별 가계부 출력
	public List<Cashbook> selectCashbookByTag(
			String memberId, String word, int beginRow, int rowPerPage) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT c.cashbook_no, c.category, c.price, c.memo, c.cashbook_date, c.updatedate, c.createdate FROM cashbook c INNER JOIN hashtag h ON c.cashbook_no = h.cashbook_no WHERE c.member_id = ? AND h.word = ? ORDER BY c.cashbook_date DESC LIMIT ?,?";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt);
			stmt.setString(1, memberId);
			stmt.setString(2, word);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("c.cashbook_no"));
				c.setCategory(rs.getString("c.category"));
				c.setPrice(rs.getInt("c.price"));
				c.setMemo(rs.getString("c.memo"));
				c.setCashbookDate(rs.getString("c.cashbook_date"));
				c.setUpdatedate(rs.getString("c.updatedate"));
				c.setCreatedate(rs.getString("c.createdate"));
				list.add(c);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 해시태그별 가계부 전체 행수
	public int selectCashbookListCnt(String memberId, String word) {
		int row = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM cashbook c INNER JOIN hashtag h ON c.cashbook_no = h.cashbook_no WHERE c.member_id = ? AND h.word = ?";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, word);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt("COUNT(*)");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
	
}