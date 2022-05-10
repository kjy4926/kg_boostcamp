package project01_survey;

public class Main {
	public static void main(String[] args) {
		SurveyController survey = new SurveyController(new GameDAO(), new SurveyDAO(), new SurveyView());
		survey.surveyStart();
	}
}