package project01_survey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// game table�� �����͸� �����ϱ� ���� Ŭ����
public class GameDAO {
	// game table ��� ���� �˻�
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
	// �帣(type) �˻�
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
	// Ʈ������ ó���� ����Ͽ� select�� ������ DML�� Connection�� �Ķ���ͷ� �޵��� �Ͽ����ϴ�.
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
	// �̸��� ���� �����ϵ��� ����
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