package com.hk.board.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hk.board.dtos.HkDto;

import hk.com.datasource.DataBase;


public class HkDao extends DataBase{

   public HkDao() {
      // TODO Auto-generated constructor stub
      super();
   }
   
   //글목록 조회 가능 : 반환값 List<HKDto> --> 여러개의 행일 경우
   public List<HkDto> getAllList(){
      List<HkDto> list = new ArrayList<>();
      Connection conn = null;
      PreparedStatement psmt = null;
      ResultSet rs = null;
      
      String sql="SELECT seq, id, title, content, regdate "
            + " FROM hkboard ORDER BY regdate DESC";
      
      try {
         conn = getConnection(); //2단계 : DB연결하기
         psmt=conn.prepareStatement(sql); //3단계:쿼리준비하기
         rs = psmt.executeQuery();//4단계:쿼리실행하기
         while(rs.next()) { //rs객체안에 데이터가 있는지 여부 확인
            HkDto dto = new HkDto();
            dto.setSeq(rs.getInt(1));
            dto.setId(rs.getString(2));
            dto.setTitle(rs.getString(3));
            dto.setContent(rs.getString(4));
            dto.setRegDate(rs.getDate(5));
            list.add(dto);
            System.out.println(dto);
         }
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         System.out.println("JDBC실패:"+getClass()+":"+"getAllList()");
         e.printStackTrace();
      }finally {
         close(rs, psmt, conn);//6단계 : 쿼리결과닫기
      }
            
      return list;
   }
   

	public boolean insertBoard(HkDto dto) {
		int count=0;
		Connection conn=null; 
		PreparedStatement psmt=null;
		
		String sql=" INSERT INTO hkboard "
				+ " VALUES(NULL,?,?,?,SYSDATE()) ";
		try {
			conn=getConnection();//db연결
			psmt=conn.prepareStatement(sql);//쿼리준비
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());//3단계 완료
			count=psmt.executeUpdate();//테이블을 수정하기 때문에 executeUpdate()
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return count>0?true:false;
		
	}
	public HkDto getBoard(int seq) {
		HkDto dto=new HkDto();
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs= null;
		String sql = " SELECT seq,id,title,content,regdate "+
					" FROM hkboard "+
					" WHERE seq= ? ";
		
		try {
			conn=getConnection();//db연결
			psmt=conn.prepareStatement(sql);//쿼리준비
			psmt.setInt(1, seq);
			rs=psmt.executeQuery();
			while(rs.next()) { //rs객체안에 데이터가 있는지 여부 확인
	            dto.setSeq(rs.getInt(1));
	            dto.setId(rs.getString(2));
	            dto.setTitle(rs.getString(3));
	            dto.setContent(rs.getString(4));
	            dto.setRegDate(rs.getDate(5));
	            System.out.println(dto);
	         }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		return dto;
	}
	//글 수정하기: update문 실행, 파라미터(seq,title,content), regdate는 쿼리에서 수정
	//결과 X -> 테이블을 수정
	public boolean updateBoard(HkDto dto) {
		int count = 0;
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs= null;
		String sql = " UPDATE hkboard "+
					" SET title=?, content=?,regdate=SYSDATE() "+
					" WHERE seq=? ";
		
		try {
			conn=getConnection();//db연결
			psmt=conn.prepareStatement(sql);//쿼리준비
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());//3단계 완료
			psmt.setInt(3, dto.getSeq());
			count=psmt.executeUpdate();//테이블을 수정하기 때문에 executeUpdate()
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		
		return count>0?true:false;
	}
	
	//글삭제: delete문 실행, 파라미터
		
	public boolean deleteBoard(int seq) {
		int count = 0;
		
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs= null;
		String sql = "DELETE FROM hkboard WHERE seq=?";
		
		try {
			conn=getConnection();//db연결
			psmt=conn.prepareStatement(sql);//쿼리준비
			psmt.setInt(1, seq);
			count=psmt.executeUpdate();//테이블을 수정하기 때문에 executeUpdate()
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, psmt, conn);
		}
		
		return count>0?true:false;
	}
	
	public boolean mulDel(String[] seqs) {
		boolean isS=true;//성공여부
		int [] count=null; //쿼리실행결과 개수
		
		Connection conn=null;
		PreparedStatement psmt=null;
		
		String sql="delete from hkboard where seq= ? ";
		
		try {
			conn=getConnection();
			//자동 커밋 - 수동 설정
			conn.setAutoCommit(false);
			psmt=conn.prepareStatement(sql);
			
			for (int i = 0; i < seqs.length; i++) {
				psmt.setString(1, seqs[i]);//seq의 타입이 String임
				psmt.addBatch();//완성된 쿼리가 배치됨 delete문 하나 저장
				
			}
			count=psmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close(null, psmt, conn);
			
			for (int i = 0; i < count.length; i++) {
				if(count[i]!=1) {
					isS=false;
					break;
					
				}
				
			}
		}

		return isS;
	}
}















