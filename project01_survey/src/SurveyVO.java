package project01_survey;

// survey table�� ������ ���� Ŭ����
public class SurveyVO {
	private String name;
	private int count;
	
	public SurveyVO(String name, int count) {
		this.name = name;
		this.count = count;
	}
	
	public String getName() {return this.name;}
	public int getCount() {return this.count;}
}