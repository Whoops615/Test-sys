package by.htp.ts.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestInfo implements Serializable {

	private static final long serialVersionUID = 3066421275306490246L;

	private int passId;
	private int id;
	private String title;
	private LocalDate dateAppointment;
	private LocalTime timePass;

	public TestInfo() {

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

	public LocalDate getDateAppointment() {
		return dateAppointment;
	}

	public void setDateAppointment(LocalDate dateAppointment) {
		this.dateAppointment = dateAppointment;
	}

	public LocalTime getTimePass() {
		return timePass;
	}

	public void setTimePass(LocalTime timePass) {
		this.timePass = timePass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPassId() {
		return passId;
	}

	public void setPassId(int passId) {
		this.passId = passId;
	}

	@Override
	public String toString() {
		return "TestInfo [passId=" + passId + ", id=" + id + ", title=" + title + ", dateAppointment=" + dateAppointment
				+ ", timePass=" + timePass + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAppointment == null) ? 0 : dateAppointment.hashCode());
		result = prime * result + id;
		result = prime * result + passId;
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
		TestInfo other = (TestInfo) obj;
		if (dateAppointment == null) {
			if (other.dateAppointment != null)
				return false;
		} else if (!dateAppointment.equals(other.dateAppointment))
			return false;
		if (id != other.id)
			return false;
		if (passId != other.passId)
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
