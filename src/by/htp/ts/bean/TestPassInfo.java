package by.htp.ts.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestPassInfo implements Serializable {

	private static final long serialVersionUID = -1365847261495849945L;

	private int passId;
	private String status;
	private boolean result;
	private double score;
	private int countQuestion;
	private int trueAnswer;

	{
		countQuestion = 0;
		trueAnswer = 0;
	}
	
	public TestPassInfo() {

	}

	public int getTrueAnswer() {
		return trueAnswer;
	}

	public void setTrueAnswer(int trueAnswer) {
		this.trueAnswer = trueAnswer;
	}

	public int getCountQuestion() {
		return countQuestion;
	}

	public void setCountQuestion(int countQuestion) {
		this.countQuestion = countQuestion;
	}


	public int getPassId() {
		return passId;
	}

	public void setPassId(int passId) {
		this.passId = passId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TestPassInfo [passId=" + passId + ", status=" + status + ", result=" + result + ", score=" + score
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + passId;
		result = prime * result + (this.result ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(score);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		TestPassInfo other = (TestPassInfo) obj;
		if (passId != other.passId)
			return false;
		if (result != other.result)
			return false;
		if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
