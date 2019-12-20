package by.htp.ts.bean;

import java.util.ArrayList;
import java.util.List;

public class Question {
	
	private String text;
	private List<Answer> answers;
	
	public Question() {
		answers = new ArrayList<Answer>();
	}
	
	public Answer getAnswer(int id) {
		return answers.get(id);
	}
	
	public void setAnswer(Answer a) {
		answers.add(a);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Question [text=" + text + ", answers=" + answers + "]";
	}

}
