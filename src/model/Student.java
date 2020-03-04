package model;

/**
 * Represents a CMUA student.
 * 
 * @author Katy
 *
 */
public class Student {
	private int studentID;
	private String name;
	private String andrewID;
	private String gender;
	private int age;
	private String program;
	private String nationality;
	private String programStartDate;
	private String photo;

	public Student() {
	};

	public Student(int studentID, String name, String andrewID, String gender, int age, String program,
			String nationality, String programStartDate, String photo) {
		this.studentID = studentID;
		this.name = name;
		this.andrewID = andrewID;
		this.gender = gender;
		this.age = age;
		this.program = program;
		this.nationality = nationality;
		this.programStartDate = programStartDate;
		this.photo = photo;

	}

	public int getStudentID() {
		return studentID;
	}

	public String getName() {
		return name;
	}

	public String getAndrewID() {
		return andrewID;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getProgram() {
		return program;
	}

	public String getNationality() {
		return nationality;
	}

	public String getProgramStartDate() {
		return programStartDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAndrewID(String andrewID) {
		this.andrewID = andrewID;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setProgramStartDate(String programStartDate) {
		this.programStartDate = programStartDate;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
