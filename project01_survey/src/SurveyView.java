package project01_survey;

import java.util.ArrayList;

// ���� ��¸��� ����ϴ� Ŭ����
// Logic ���� print�� �����Ѵ�.

public class SurveyView {
	// ���� �޴� ���
	public void printStartMenu() {
		System.out.println("---------------------");
		System.out.println("1.�������� �����ϱ�");
		System.out.println("2.�������� ��� ����");
		System.out.println("3.���� �߰��ϱ�");
		System.out.println("4.���� �����ϱ�");
		System.out.println("5.Ư�� �帣 ��� �˻�");
		System.out.println("6.���� ����");
		System.out.println("---------------------");
		System.out.print("�۾� �Է�>> ");
	}
	// ��� ��� �� ���� ��� ���� �޴�
	public void printResultMenu() {
		System.out.println("---------------------");
		System.out.println("1.�⺻ ��� ����");
		System.out.println("2.�������� ��� ����");
		System.out.println("3.�������� ��� ����");
		System.out.println("---------------------");
	}
	// �Է� ������ ���
	public void printView(ArrayList<SurveyVO> rows) {
		System.out.println("---------------------");
		for(int i=1; i<=rows.size();i++) {
			SurveyVO row = rows.get(i-1);
			System.out.println(String.format("%2d.%s => %dǥ", i, row.getName(), row.getCount()));
		}
		System.out.println("---------------------");
	}
	// �Է� �����Ͱ� game.type�� ���
	public void printTypeView(ArrayList<String> rows) {
		for(int i=1; i<=rows.size();i++) {
			String row = rows.get(i-1);
			System.out.println(String.format("%2d.%s", i, row));
		}
	}
}
