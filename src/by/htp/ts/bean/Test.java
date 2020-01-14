package by.htp.ts.bean;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {

	private static final long serialVersionUID = -3712944973171370881L;

	private int id;
	private String title;
	private LocalTime timePass;
	private List<Question> questions;

	public Test() {
		this.questions = new ArrayList<Question>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalTime getTimePass() {
		return timePass;
	}

	public void setTimePass(LocalTime timePass) {
		this.timePass = timePass;
	}

	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestion(Question question) {
		questions.add(question);
	}
	
	public Question getQuestion(int index) {
		return questions.get(index);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", title=" + title + ", timePass=" + timePass + ", questions=" + questions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + ((timePass == null) ? 0 : timePass.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (id != other.id)
			return false;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (timePass == null) {
			if (other.timePass != null)
				return false;
		} else if (!timePass.equals(other.timePass))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
