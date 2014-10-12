package example.chennaidataportal;

import java.io.Serializable;

public class SchoolData implements Serializable {

	String schoolID, staffId, staffName, StudentID, StudentName, classes;
	boolean selected = false;

	public SchoolData() {
		// TODO Auto-generated constructor stub
	}
	
	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public SchoolData(String schoolID, String staffId, String staffName,
			String studentID, String studentName, String class1,
			boolean selected) {
		super();
		this.schoolID = schoolID;
		this.staffId = staffId;
		this.staffName = staffName;
		StudentID = studentID;
		StudentName = studentName;
		classes = class1;
		this.selected = selected;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStudentID() {
		return StudentID;
	}

	public void setStudentID(String studentID) {
		StudentID = studentID;
	}

	public String getStudentName() {
		return StudentName;
	}

	public void setStudentName(String studentName) {
		StudentName = studentName;
	}

	public String getclasses() {
		return classes;
	}

	public void setClass(String class1) {
		classes = class1;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
