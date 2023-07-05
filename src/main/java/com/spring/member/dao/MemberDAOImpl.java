package com.spring.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate; //JDBC 사용을 도와주는 템플릿
import com.spring.member.vo.MemberVO;
import org.springframework.jdbc.core.RowMapper;
public class MemberDAOImpl implements MemberDAO {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource); // 주어진 연결을 하는 데이터소스를 가지고 JDBC 템플릿 활용 //Construct a new
															// JdbcTemplate, given a DataSource to obtain connections
															// from.
	}

	@Override
	public List<MemberVO> selectAllMembers() throws DataAccessException {
		String query = "select id,pwd,name,email,joinDate" + " from t_member " + " order by joinDate desc";
		
		
		 List<MemberVO> membersList= new ArrayList<MemberVO>();
		
		 
		 //this.jdbcTemplate.query(null, null)
		 
		 //RowMapper 인터페이스란 결과셋의 각 행을 매핑시켜 줌(mapping rows of a ResultSet)
		 membersList =  this.jdbcTemplate.query(query, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				MemberVO memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setJoinDate(rs.getDate("joinDate"));
				
				return memberVO;
			}
			 
		 });
		 
		 
		return membersList;
	}

	
	
	@Override
	public void addMember(MemberVO memberVO) throws DataAccessException{
		
		String id=memberVO.getId();
		String pwd=memberVO.getPwd();
		String name=memberVO.getName();
		String email=memberVO.getEmail();
		
		String query = "insert into t_member(id, pwd, name, email) VALUES (" +  "'" + id + "' ," +  "'" + pwd + "' ," + "'" + name + "' ," + "'" + email + "') "; 
		
				
		System.out.println(query);
		int resultInt=jdbcTemplate.update(query);
		System.out.println(resultInt);
	}

	
	
	@Override
	public void delMember(String id) throws DataAccessException {
	
		 String sql = "DELETE FROM t_member WHERE id = ?";

		 jdbcTemplate.update(sql, id);
	}

	
	
	
}
