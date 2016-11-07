package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bisbn, bimgbase64, btitle, bauthor, bprice "
					   + "from book where btitle like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean update(String isbn, String title, String author, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			System.out.println(isbn);
			System.out.println(title);
			System.out.println(author);
			System.out.println(price);
			String sql = "update book set btitle=?, bauthor=?, bprice=? where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);
			
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	public boolean delete(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			System.out.println(isbn);
			
			String sql = "delete from book where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			System.out.println(isbn);
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	public String about(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		String result = null;
		try {
	
			
			String sql = "select bisbn, bdate, bpage, btranslator, bsupplement from book where bisbn = ? ";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			System.out.println(isbn);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			JSONObject obj = new JSONObject();
			obj.put("isbn", rs.getString("bisbn"));
			obj.put("date", rs.getString("bdate"));
			obj.put("page", rs.getString("bpage"));
			obj.put("translator", rs.getString("btranslator"));
			obj.put("supplement", rs.getString("bsupplement"));
			
			result = obj.toJSONString();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	public boolean insert(String isbn, String title, String date, String page,  String price, String author, String translator, String supplement, String publisher, String imgurl, String imgbase64) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			
			String sql = "insert into book (bisbn,btitle,bdate,bpage,bprice,bauthor,btranslator,bsupplement,bpublisher,bimgurl,bimgbase64) value (?,?,?,?,?,?,?,?,?,?,?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setInt(4,Integer.parseInt(page));
			pstmt.setInt(5,Integer.parseInt(price));
			pstmt.setString(6, author);
			pstmt.setString(7, translator);
			pstmt.setString(8, supplement);
			pstmt.setString(9, publisher);
			pstmt.setString(10, imgurl);
			pstmt.setString(11, imgbase64);
			
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	public boolean newUser(String ID,String fname,String lname,String email,String pw) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			
			String sql = "insert into user (ID,fname,lname,email,pw) value (?,?,?,?,?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, email);
			pstmt.setString(5, pw);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public String userLogin(String ID, String pw) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select pw from user where ID = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			
			rs.next();
			if(rs.getString("pw").equals(pw)){
				result = ID;
			
			}
			else{
				result="error";
			}
			System.out.println(ID + pw +"DAO");

		}
			catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	
	
		}



public boolean insertComment( String isbn, String title, String author, String date, String text) {
	Connection con = DBTemplate.getConnection();
	PreparedStatement pstmt = null;
	
	boolean result = false;
	try {
		
		String sql = "insert into book_comment (bisbn,ctitle,cauthor,cdate,ctext) value (?,?,?,?,?)";
		pstmt= con.prepareStatement(sql);
		pstmt.setString(1, isbn);
		pstmt.setString(2, title);
		pstmt.setString(3, author);
		pstmt.setString(4, date);
		pstmt.setString(5, text);
		
		
		
		int count = pstmt.executeUpdate();
		// 결과값은 영향을 받은 레코드의 수
		if( count == 1 ) {
			result = true;
			// 정상처리이기 때문에 commit
			DBTemplate.commit(con);
		} else {
			DBTemplate.rollback(con);
		}
		
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		DBTemplate.close(pstmt);
		DBTemplate.close(con);
	} 
	return result;
}
public String commentSelect(String keyword) {
	
	Connection con = DBTemplate.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String result = null;
	try {
		String sql = "select b.bisbn, c.cid, b.btitle, b.bimgbase64, c.ctitle, c.cauthor, c.cdate, c.ctext from book b"+
				" join book_comment c on b.bisbn = c.bisbn where b.btitle like ? order by c.cid desc";
		pstmt= con.prepareStatement(sql);
		pstmt.setString(1, "%" + keyword + "%");
		rs = pstmt.executeQuery();
		JSONArray arr = new JSONArray();
		while(rs.next()) {
			JSONObject obj = new JSONObject();
			obj.put("isbn", rs.getString("bisbn"));
			obj.put("cid", rs.getString("cid"));
			obj.put("btitle", rs.getString("btitle"));
			obj.put("img", rs.getString("bimgbase64"));
			obj.put("ctitle", rs.getString("ctitle"));
			obj.put("author", rs.getString("cauthor"));
			obj.put("date", rs.getString("cdate"));
			obj.put("text", rs.getString("ctext"));
			arr.add(obj);
		}
		result = arr.toJSONString();
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		DBTemplate.close(rs);
		DBTemplate.close(pstmt);
		DBTemplate.close(con);
	} 
	return result;
}

public boolean deleteComment(String cid) {
	Connection con = DBTemplate.getConnection();
	PreparedStatement pstmt = null;
	
	boolean result = false;
	try {
		
		String sql = "delete from book_comment where cid=?";
		pstmt= con.prepareStatement(sql);
		pstmt.setString(1, cid);
		
		int count = pstmt.executeUpdate();
		// 결과값은 영향을 받은 레코드의 수
		if( count == 1 ) {
			result = true;
			// 정상처리이기 때문에 commit
			DBTemplate.commit(con);
		} else {
			DBTemplate.rollback(con);
		}
		
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		DBTemplate.close(pstmt);
		DBTemplate.close(con);
	} 
	return result;
}
public String lendableList(String keyword) {
	
	Connection con = DBTemplate.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String result = null;
	try {
		String sql = "select bisbn, btitle, bimgbase64 from lendbook where lender is null and btitle like ?";
		pstmt= con.prepareStatement(sql);
		pstmt.setString(1, "%" + keyword + "%");
		rs = pstmt.executeQuery();
		JSONArray arr = new JSONArray();
		while(rs.next()) {
			JSONObject obj = new JSONObject();
			obj.put("isbn", rs.getString("bisbn"));
			obj.put("title", rs.getString("btitle"));
			obj.put("img", rs.getString("bimgbase64"));
		
			arr.add(obj);
		}
		result = arr.toJSONString();
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		DBTemplate.close(rs);
		DBTemplate.close(pstmt);
		DBTemplate.close(con);
	} 
	return result;
}


public boolean lendBook(String isbn, String cid, String date) {
	Connection con = DBTemplate.getConnection();
	PreparedStatement pstmt = null;
	
	boolean result = false;
	try {
		
		String sql = "update lendbook set lender = ? ldate = ? where isbn=?";
		pstmt= con.prepareStatement(sql);
		pstmt.setString(1, cid);
		pstmt.setString(2, date);
		pstmt.setString(3, isbn);
		
		int count = pstmt.executeUpdate();
		// 결과값은 영향을 받은 레코드의 수
		if( count == 1 ) {
			result = true;
			// 정상처리이기 때문에 commit
			DBTemplate.commit(con);
		} else {
			DBTemplate.rollback(con);
		}
		
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		DBTemplate.close(pstmt);
		DBTemplate.close(con);
	} 
	return result;
}










}
