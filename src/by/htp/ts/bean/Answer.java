package by.htp.ts.bean;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = 4470561438938788006L;

	private int id;
	private String content;
	boolean rightness;

	public Answer() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRightness() {
		return rightness;
	}

	public void setRightness(boolean rightness) {
		this.rightness = rightness;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", content=" + content + ", rightness=" + rightness + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + (rightness ? 1231 : 1237);
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
		Answer other = (Answer) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (rightness != other.rightness)
			return false;
		return true;
	}

}
