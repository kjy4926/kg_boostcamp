package project01_survey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// survey table�� �����͸� �����ϱ� ���� Ŭ����
public class SurveyDAO {
	public ArrayList<SurveyVO> selectAll(){
		ArrayList<SurveyVO> result = new ArrayList<>();
		String sql = "SELECT * FROM survey";
		
		try(PreparedStatement pst = JdbcConnector.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery()){
			while(rs.next()) {
				result.add(new SurveyVO(rs.getString(1), rs.getInt(2)));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// �������� ǥ�� ���� ��������/�������� ���ĵ� ���
	public ArrayList<SurveyVO> selectAllOrdered(boolean reverse){
		ArrayList<SurveyVO> result = new ArrayList<>();
		String sql = "SELECT * FROM survey ORDER BY count ASC";
		if(reverse) sql = "SELECT * FROM survey ORDER BY count DESC";
		try(PreparedStatement pst = JdbcConnector.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery()){
			while(rs.next()) {
				result.add(new SurveyVO(rs.getString(1), rs.getInt(2)));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// game table�� inner join�� ���� �ش� type�� �ش��ϴ� ���� ����� ���
	public ArrayList<SurveyVO> selectByTypeJoinWithGame(String type){
		ArrayList<SurveyVO> result = new ArrayList<>();
		String sql = "SELECT survey.name, survey.count "
				+ "FROM game, survey "
				+ "WHERE game.name = survey.name "
				+ "AND game.type = ?";
		try(PreparedStatement pst = JdbcConnector.getConnection().prepareStatement(sql);){
			pst.setString(1, type);
			try(ResultSet rs = pst.executeQuery()){
				while(rs.next()) {
					result.add(new SurveyVO(rs.getString(1), rs.getInt(2)));
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean insert(SurveyVO svo, Connection conn) {
		boolean result = false;
		String sql = "INSERT INTO survey VALUES(?, ?)";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, svo.getName());
			pst.setInt(2, 0);
			pst.executeUpdate();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public int updateCount(SurveyVO svo, Connection conn) {
		int result = -1;
		String sql = "UPDATE survey SET count = count+1 WHERE name = ?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, svo.getName());
			result = pst.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// �̸��� ���� �����ϵ��� ����
	public int delete(String name, Connection conn) {
		int result = -1;
		String sql = "DELETE FROM survey WHERE name = ?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, name);
			result = pst.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
