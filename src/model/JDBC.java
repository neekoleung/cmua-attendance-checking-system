package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * The database engine. 
 * 
 * StudentID: 1 : Katy, 2 : Wendy, 3 : Peter, 4 : Neeko, 5 : Alisha
 * 
 * Category: 
 * 1, 'Ask for stapler' 
 * 2, 'Hand in or collect assignment' 
 * 3, 'Meet a faculty' 
 * 4, 'Request for locker' 
 * 5, 'Chat with Kimberly' 
 * 6, 'Complaints' 
 * 7, 'Ask questions (finance)' 
 * 8, 'Ask questions (IT)' 
 * 9, 'Ask questions (marketing)' 
 * 10, 'Ask questions (career service)'
 * 
 * @author Katy
 *
 */
public class JDBC {

	private static String url = "jdbc:derby:C:\\Users\\pafranci\\MyDB;create=true";
	private static String username = "derbyconnection";
	private static String password = "derbyconnection";

	/**
	 * This method is for inserting a visit record. When someone visits the front
	 * desk, it will automatically create a Date object and input to database as a
	 * String, so you just need to input studentID and category number
	 * 
	 * @param studentID
	 * @param purposeNum
	 */
	public static void InsertRecord(int studentID, int purposeNum) {

		Date now = new Date();
		String format = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String date = simpleDateFormat.format(now);

		String query = "INSERT INTO VISIT_RECORD VALUES(" + studentID + ",'" + date + "'," + purposeNum + ")";
		System.out.println(query);

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection(url, username, password)) {

			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
	}

	/**
	 * This method is for inserting information of a student (when someone unknown
	 * visit the front desk and needs to create a new user into our system)
	 * 
	 * @param studentID
	 * @param studentName
	 * @param andrewID
	 * @param gender
	 * @param age
	 * @param program
	 * @param nationality
	 * @param programStart
	 * @param photo
	 */
	public static void InsertStudent(int studentID, String studentName, String andrewID, String gender, int age,
			String program, String nationality, String programStart, String photo) {

		String query = "INSERT INTO STUDENT VALUES(" + studentID + ",'" + studentName + "','" + andrewID + "','"
				+ gender + "'," + age + ",'" + program + "','" + nationality + "','" + programStart + "','" + photo
				+ "')";
		System.out.println(query);

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection(url, username, password)) {

			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
	}

	/**
	 * Retrieves the announcements for the student.
	 * 
	 * @param studentID
	 * @return a ArrayList contains the String of announcements for that student
	 */
	public static String Announcement(int studentID) {
		String query = "SELECT ANNOUNCEMENT FROM ANNOUNCEMENT WHERE STUDENTID = " + studentID;
		System.out.println(query);

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String returnAnnouncement = "";
		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String announcement = rs.getString("Announcement");
				returnAnnouncement += (announcement + "\n");
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
		return returnAnnouncement;
	}

	/**
	 * Retrieves the details of this student.
	 * 
	 * @param studentID
	 * @return a ArrayList that contains Strings of student information (use for
	 *         dashboard) (ID, name, andrewID, gender, age, program, nationality,
	 *         programStartDate, photo)
	 */
	public static Student StudentInfo(int studentID) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String query = "SELECT * FROM STUDENT WHERE STUDENTID = " + studentID;
		System.out.println(query);
		Student student = new Student();
		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				int ID = rs.getInt("StudentID");
				String name = rs.getString("Name");
				String andrewID = rs.getString("AndrewID");
				String gender = rs.getString("Gender");
				int age = rs.getInt("Age");
				String program = rs.getString("Program");
				String nationality = rs.getString("Nationality");
				String programStartDate = rs.getString("ProgramStartDate");
				String photo = rs.getString("Photo");

				student.setStudentID(ID);
				student.setName(name);
				student.setAndrewID(andrewID);
				student.setGender(gender);
				student.setAge(age);
				student.setProgram(program);
				student.setNationality(nationality);
				student.setProgramStartDate(programStartDate);
				student.setPhoto(photo);
			}
			System.out.println("Student object created: " + student.getName());
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
		return student;
	}

	/**
	 * Retrieves the visit records for a student
	 * 
	 * @param studentID
	 * @return a ArrayList that contains String of (date + purpose) for that student
	 */
	public static ArrayList<Record> StudentRecord(int studentID) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String query = "SELECT VISIT_RECORD.STUDENTID,VISIT_RECORD.DATE ,PURPOSE.PURPOSE	\n"
				+ "FROM VISIT_RECORD INNER JOIN PURPOSE ON VISIT_RECORD.PURPOSENUM = PURPOSE.PURPOSENUM\n"
				+ "WHERE VISIT_RECORD.STUDENTID = " + studentID;
		System.out.println(query);
		ArrayList<Record> studentRecords = new ArrayList<Record>();
		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				int ID = rs.getInt("STUDENTID");
				String date = rs.getString("DATE");
				String reason = rs.getString("PURPOSE");
				Record record = new Record(date, reason);
				studentRecords.add(record);
			}

		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
		return studentRecords;
	}

	/**
	 * Counts the total number of visits for this student
	 * 
	 * @param studentID
	 * @param purposeNum
	 * @return a count of visit records for that studentID and purposeNum
	 */
	public static int getVisitCount(int studentID, int purposeNum) {

		int count = 0;

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String query = "SELECT VISIT_RECORD.STUDENTID, VISIT_RECORD.PURPOSENUM	\n" + "FROM VISIT_RECORD \n"
				+ "WHERE VISIT_RECORD.STUDENTID = " + studentID + "AND VISIT_RECORD.PURPOSENUM = " + purposeNum;

		System.out.println(query);

		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				count++;
			}

		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
		return count;
	}

	/**
	 * Input date range (make sure the format is yyyy-mm-dd) and category number
	 * 
	 * @param srtDate
	 * @param endDate
	 * @param category
	 * @return String in format of Date + Name Use for the first report
	 */
	public static ArrayList<Report1> ReportByCategories(String srtDate, String endDate, int category) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String query = "SELECT STUDENT.NAME,VISIT_RECORD.DATE \n" + "FROM VISIT_RECORD\n"
				+ "INNER JOIN STUDENT ON VISIT_RECORD.STUDENTID = STUDENT.STUDENTID\n" + "WHERE VISIT_RECORD.DATE >= '"
				+ srtDate + "' AND VISIT_RECORD.DATE <= '" + endDate + "' AND VISIT_RECORD.PURPOSENUM = " + category;
		System.out.println(query);
		ArrayList<Report1> records = new ArrayList<Report1>();
		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String name = rs.getString("NAME");
				String date = rs.getString("DATE");
				Report1 record = new Report1(name, date);
				records.add(record);
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}
		}
		return records;
	}

	/**
	 * Input date range & category number(1~10) & and gender ('F' or 'M') It will
	 * return a integer that indicates Use for the pie chart data.
	 * 
	 * @param srtDate
	 * @param endDate
	 * @param category
	 * @param gender
	 * @return how many times student in this gender had visited front desk for this
	 *         purpose in the date range
	 */
	public static int NumberOfRecords(String srtDate, String endDate, int category, char gender) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String query = "SELECT VISIT_RECORD.STUDENTID\n" + "FROM VISIT_RECORD \n"
				+ "INNER JOIN STUDENT ON VISIT_RECORD.STUDENTID = STUDENT.STUDENTID\n" + "WHERE VISIT_RECORD.DATE <= '"
				+ endDate + "' AND VISIT_RECORD.DATE >= '" + srtDate + "' AND VISIT_RECORD.PURPOSENUM = " + category
				+ " AND STUDENT.Gender = '" + gender + "'";
		ArrayList<String> studentIDs = new ArrayList<String>();
		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String id = rs.getString("STUDENTID");
				studentIDs.add(id);
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}

		}
		return studentIDs.size();
	}

	/**
	 * Get the last visit date of this student.
	 * 
	 * @param studentID
	 * @return the last visit date of this student
	 */
	public static String getLastVisit(int studentID) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String query = "SELECT MAX(VISIT_RECORD.DATE) \n" + "FROM VISIT_RECORD \n" + "WHERE STUDENTID = " + studentID;
		System.out.println(query);
		String date = "";
		try (Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				date = rs.getString("1");
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQLState:  " + ex.getSQLState());
				System.out.println("Error Code:" + ex.getErrorCode());
				System.out.println("Message:   " + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
			}

		}
		System.out.println(date);
		return date;
	}
}
