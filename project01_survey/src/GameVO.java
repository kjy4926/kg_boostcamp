package project01_survey;

// game table의 정보를 담을 클래스
public class GameVO {
	private String name;
	private String type;
	
	public GameVO(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {return this.name;}
	public String getType() {return this.type;}
}