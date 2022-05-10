package project01_survey;

// survey table의 정보를 담을 클래스
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