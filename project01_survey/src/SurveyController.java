package project01_survey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

// ���� GameDAO�� SurveyDAO�� ���� ������ ������ ������ Ŭ����
// GameDAO�� SurveyDAO�� �����͸� �����ϴ� ������� �˰� ������
// ��� ���������� ���� ������ ������ ���� �ʴ�.
// �̸� �˷��ֱ�  ����Ŭ������ SurveyController�̴�.
// ���������� ����ڰ� ������ ������ ���� ����ϴ� �� �������̽� Ŭ����
public class SurveyController {
	private GameDAO gdao;
	private SurveyDAO sdao;
	private SurveyView view;
	private BufferedReader br;
	// constructor
	public SurveyController(GameDAO gdao, SurveyDAO sdao, SurveyView view) {
		this.gdao = gdao;
		this.sdao = sdao;
		this.view = view;
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	// ���� ���α׷� ����
	// ����ڴ� �ش� �Լ��� ���� ������ �����ϰ� �ȴ�.
	// �ٸ� ����� ���������� ����ǹǷ� �� �Լ��� �����ϰ� ��� private ó���Ͽ���.
	public void surveyStart() {
		try{
			int oper = -1;
			while(oper != 6){
				view.printStartMenu();
				oper = Integer.parseInt(br.readLine());
				controll(oper);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// operation controller
	private void controll(int op) {
		switch(op) {
		case 1:
			survey();
			break;
		case 2:
			view.printResultMenu();
			showResult();
			break;
		case 3:
			insertGame();
			break;
		case 4:
			deleteGame();
			break;
		case 5:
			showTypeView();
			break;
		default:
			break;
		}
	}
	// ���� ����
	private void survey() {
		ArrayList<SurveyVO> rows = sdao.selectAll();
		try(Connection conn = JdbcConnector.getConnection()){
			int n;
			while(true){
				showView(rows);
				System.out.print("(���� : 0)\n��ȣ �Է�>> ");
				n = Integer.parseInt(br.readLine());
				if(n==0) break;
				if(n>0 && n<=rows.size()) {
					sdao.updateCount(rows.get(n-1), conn);
					rows = sdao.selectAll();
				}
				else {continue;}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ���� ���
	private void showView(ArrayList<SurveyVO> rows) {
		view.printView(rows);
	}
	// �帣 ��� ���
	private void showType(ArrayList<String> rows) {
		view.printTypeView(rows);
	}
	// ���õ� ���� ��Ŀ� ���� ��� ���
	private void showResult() {
		try {
			System.out.print("���� ����>> ");
			int n = Integer.parseInt(br.readLine());
			switch(n) {
			case 1:
				showView(sdao.selectAll());
				break;
			case 2:
				showView(sdao.selectAllOrdered(false));
				break;
			case 3:
				showView(sdao.selectAllOrdered(true));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Ư�� �帣�� ���� ���� ��� ���
	private void showTypeView() {
		ArrayList<String> rows = gdao.selectAllType();
		try(Connection conn = JdbcConnector.getConnection()){
				int n;
				while(true){
					showType(rows);
					System.out.print("(���� : 0)\n��ȣ �Է�>> ");
					n = Integer.parseInt(br.readLine());
					if(n==0) break;
					if(n>0 && n<=rows.size()) {
						showView(sdao.selectByTypeJoinWithGame(rows.get(n-1)));
					}
					else {continue;}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	// ���� �߰�
	private boolean insertGame() {
		boolean result = false;
		String name;
		String type;
		try(Connection conn = JdbcConnector.getConnection()){
			System.out.print("���� �̸� �Է� : ");
			name = br.readLine();
			System.out.print("���� �帣 �Է� : ");
			type = br.readLine();
			// AutoCommit ����
			conn.setAutoCommit(false);
			// Ʈ����� -> game table insert �� survey table insert ����
			boolean flag1 = gdao.insert(new GameVO(name, type), conn);
			boolean flag2 = sdao.insert(new SurveyVO(name, 0), conn);
			// �� ��� �̻���� ������ٸ� commit
			if(flag1 && flag2) {
				conn.commit();
				result = true;
			}
			// ������ �߻��E�ٸ� rollback
			else conn.rollback();
			// AutoCommit ����
			conn.setAutoCommit(true);
		}catch(IOException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// ���� ����
	// FK OPTION -> ON DELETE CASCADE �ɼ����� �����صξ��� ������
	// game table������ �����ϴ��� survey table�������� ���ŵ˴ϴ�.
	private int deleteGame() {
		int result = -1;
		try(Connection conn = JdbcConnector.getConnection()){
			System.out.print("������ ���� �̸� �Է� : ");
			String name = br.readLine();
			result = gdao.delete(name, conn);
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
