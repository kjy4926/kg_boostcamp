package project01_survey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// game table의 데이터를 조작하기 위한 클래스
public class GameDAO {
	// game table 모든 정보 검색
	public ArrayList<GameVO> selectAll(){
		ArrayList<GameVO> result = new ArrayList<GameVO>();
		String sql = "SELECT * FROM game";
		
		try(PreparedStatement pst = JdbcConnector.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery()) {
			while(rs.next()) {
				result.add(new GameVO(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	// 장르(type) 검색
	public ArrayList<String> selectAllType(){
		ArrayList<String> result = new ArrayList<>();
		String sql = "SELECT DISTINCT type FROM game";
		
		try(PreparedStatement pst = JdbcConnector.getConnection().prepareStatement(sql)) {
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	// 트랜젝션 처리를 고려하여 select를 제외한 DML은 Connection을 파라미터로 받도록 하였습니다.
	public boolean insert(GameVO gvo, Connection conn) {
		boolean result = false;
		String sql = "INSERT INTO game VALUES(?, ?)";
		try(PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, gvo.getName());
			pst.setString(2, gvo.getType());
			pst.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	// 이름을 통해 삭제하도록 구현
	public int delete(String name, Connection conn) {
		int result = -1;
		String sql = "DELETE FROM game WHERE name = ?";
		try(PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, name);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}