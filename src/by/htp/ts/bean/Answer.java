package by.htp.ts.bean;

public class Answer {
	
	private String text;
	boolean rightness;
	
	public Answer() {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRightness() {
		return rightness;
	}

	public void setRightness(boolean rightness) {
		this.rightness = rightness;
	}

	@Override
	public String toString() {
		return "Answer [text=" + text + ", rightness=" + rightness + "]";
	}

}
