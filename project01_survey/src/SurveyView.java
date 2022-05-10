package project01_survey;

import java.util.ArrayList;

// 오직 출력만을 담당하는 클래스
// Logic 없이 print만 수행한다.

public class SurveyView {
	// 시작 메뉴 출력
	public void printStartMenu() {
		System.out.println("---------------------");
		System.out.println("1.설문조사 참여하기");
		System.out.println("2.설문조사 결과 보기");
		System.out.println("3.게임 추가하기");
		System.out.println("4.게임 삭제하기");
		System.out.println("5.특정 장르 결과 검색");
		System.out.println("6.설문 종료");
		System.out.println("---------------------");
		System.out.print("작업 입력>> ");
	}
	// 결과 출력 시 정렬 방식 선택 메뉴
	public void printResultMenu() {
		System.out.println("---------------------");
		System.out.println("1.기본 결과 보기");
		System.out.println("2.오름차순 결과 보기");
		System.out.println("3.내림차순 결과 보기");
		System.out.println("---------------------");
	}
	// 입력 데이터 출력
	public void printView(ArrayList<SurveyVO> rows) {
		System.out.println("---------------------");
		for(int i=1; i<=rows.size();i++) {
			SurveyVO row = rows.get(i-1);
			System.out.println(String.format("%2d.%s => %d표", i, row.getName(), row.getCount()));
		}
		System.out.println("---------------------");
	}
	// 입력 데이터가 game.type인 경우
	public void printTypeView(ArrayList<String> rows) {
		for(int i=1; i<=rows.size();i++) {
			String row = rows.get(i-1);
			System.out.println(String.format("%2d.%s", i, row));
		}
	}
}
