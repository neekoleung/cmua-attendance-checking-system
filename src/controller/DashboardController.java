package controller;

import model.JDBC;
import model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Neeko
 *
 */
public class DashboardController implements Initializable {

	@FXML
	private Label lbName;

	@FXML
	private Label lbAge;

	@FXML
	private Label lbAndrewID;

	@FXML
	private Label lbGender;

	@FXML
	private Label lbProgram;

	@FXML
	private Label lbNationality;

	@FXML
	private Label lbStartDate;

	@FXML
	private Label lbLastVisitDate;

	@FXML
	private Label lbTotalVisitTimes;

	@FXML
	private Label lbAnouncement;

	@FXML
	private ImageView ivPhoto;

	@FXML
	private PieChart chart;

	/**
	 * Populates the dashboard with the info of the recognized student.
	 */
	public void initializeDashboard() {

		// SET PHOTO
		System.out.println("/images/" + LoginController.student.getPhoto());
		Image image = new Image("/images/" + LoginController.student.getPhoto());
		ivPhoto.setImage(image);

		// SET DISPLAY INFO
		lbName.setText(LoginController.student.getName());
		lbAge.setText(LoginController.student.getAge() + "");
		lbAndrewID.setText(LoginController.student.getAndrewID());
		lbGender.setText(LoginController.student.getGender());
		lbProgram.setText(LoginController.student.getProgram());
		lbNationality.setText(LoginController.student.getNationality());
		lbStartDate.setText(LoginController.student.getProgramStartDate());

		// SET LAST VISIT DATE
		lbLastVisitDate.setText(JDBC.getLastVisit(LoginController.student.getStudentID()));
		// lbLastVisitDate.setText("PLACEHOLDER");

		// SET TOTAL VISIT TIMES
		lbTotalVisitTimes.setText(JDBC.StudentRecord(LoginController.student.getStudentID()).size() + "");

		// POPULATE PIE CHART
		ArrayList<Record> records = JDBC.StudentRecord(LoginController.student.getStudentID());

		for (Record record : records) {
			System.out.println(record.getDate() + " " + record.getReason());
		}

		int studentId = LoginController.student.getStudentID();

		ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
				new PieChart.Data("Stapler", JDBC.getVisitCount(studentId, 1)),
				new PieChart.Data("Assignment", JDBC.getVisitCount(studentId, 2)),
				new PieChart.Data("Meet faculty", JDBC.getVisitCount(studentId, 3)),
				new PieChart.Data("Locker", JDBC.getVisitCount(studentId, 4)),
				new PieChart.Data("Chat with Kim", JDBC.getVisitCount(studentId, 5)),
				new PieChart.Data("Complaints", JDBC.getVisitCount(studentId, 6)),
				new PieChart.Data("Question (finance)", JDBC.getVisitCount(studentId, 7)),
				new PieChart.Data("Question (IT)", JDBC.getVisitCount(studentId, 8)),
				new PieChart.Data("Question (marketing)", JDBC.getVisitCount(studentId, 9)),
				new PieChart.Data("Question (career)", JDBC.getVisitCount(studentId, 10)));
		chart.setData(data);
		chart.setTitle("Reason for Visit");
		chart.setLabelsVisible(true);

		// SET ANNOUNCEMENTS
		System.out.println(JDBC.Announcement(LoginController.student.getStudentID()));
		lbAnouncement.setText(JDBC.Announcement(LoginController.student.getStudentID()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeDashboard();
	}
}
