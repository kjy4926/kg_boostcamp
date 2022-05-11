package project01_survey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

// 실제 GameDAO와 SurveyDAO를 통헤 데이터 조작을 제어할 클래스
// GameDAO와 SurveyDAO는 데이터를 조작하는 방법만을 알고 있으며
// 어떻게 조작할지에 대한 정보는 가지고 있지 않다.
// 이를 알려주기  위한클래스가 SurveyController이다.
// 실질적으로 사용자가 데이터 조작을 위해 사용하는 될 인터페이스 클래스
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
	// 설문 프로그램 시작
	// 사용자는 해당 함수를 통해 조작을 수행하게 된다.
	// 다른 기능은 내부적으로 수행되므로 이 함수를 제외하곤 모두 private 처리하였다.
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
	// 설문 진행
	private void survey() {
		ArrayList<SurveyVO> rows = sdao.selectAll();
		try(Connection conn = JdbcConnector.getConnection()){
			int n;
			while(true){
				showView(rows);
				System.out.print("(종료 : 0)\n번호 입력>> ");
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
	// 정보 출력
	private void showView(ArrayList<SurveyVO> rows) {
		view.printView(rows);
	}
	// 장르 목록 출력
	private void showType(ArrayList<String> rows) {
		view.printTypeView(rows);
	}
	// 선택된 정렬 방식에 따른 결과 출력
	private void showResult() {
		try {
			System.out.print("정렬 선택>> ");
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
	// 특정 장르의 게임 설문 결과 출력
	private void showTypeView() {
		ArrayList<String> rows = gdao.selectAllType();
		try(Connection conn = JdbcConnector.getConnection()){
				int n;
				while(true){
					showType(rows);
					System.out.print("(종료 : 0)\n번호 입력>> ");
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
	// 게임 추가
	private boolean insertGame() {
		boolean result = false;
		String name;
		String type;
		try(Connection conn = JdbcConnector.getConnection()){
			System.out.print("게임 이름 입력 : ");
			name = br.readLine();
			System.out.print("게임 장르 입력 : ");
			type = br.readLine();
			// AutoCommit 해제
			conn.setAutoCommit(false);
			// 트랜잭션 -> game table insert 후 survey table insert 수행
			boolean flag1 = gdao.insert(new GameVO(name, type), conn);
			boolean flag2 = sdao.insert(new SurveyVO(name, 0), conn);
			// 둘 모두 이상없이 수행됬다면 commit
			if(flag1 && flag2) {
				conn.commit();
				result = true;
			}
			// 문제가 발생핬다면 rollback
			else conn.rollback();
			// AutoCommit 복구
			conn.setAutoCommit(true);
		}catch(IOException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// 게임 삭제
	// FK OPTION -> ON DELETE CASCADE 옵션으로 지정해두었기 때문에
	// game table에서만 삭제하더라도 survey table에서까지 제거됩니다.
	private int deleteGame() {
		int result = -1;
		try(Connection conn = JdbcConnector.getConnection()){
			System.out.print("삭제할 게임 이름 입력 : ");
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
