package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.JDBC;
import java.io.IOException;

/**
 * @author Neeko
 *
 */
public class PieReportController {
	@FXML
	private Button btnReport;

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private MenuButton mbReason;

	@FXML
	private MenuItem one;

	@FXML
	private MenuItem two;

	@FXML
	private MenuItem three;

	@FXML
	private MenuItem four;

	@FXML
	private MenuItem five;

	@FXML
	private MenuItem six;

	@FXML
	private MenuItem seven;

	@FXML
	private MenuItem eight;

	@FXML
	private MenuItem nine;

	@FXML
	private MenuItem ten;

	@FXML
	private PieChart pie1;

	@FXML
	private PieChart pie2;

	static int reason;

	/**
	 * Handles the actions for different events at the report.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void handleClicks(ActionEvent actionEvent) throws IOException {

		if (actionEvent.getSource() == one) {
			reason = 1;
			mbReason.setText("Ask for stapler");
		} else if (actionEvent.getSource() == two) {
			reason = 2;
			mbReason.setText("Hand in or collect assignment");
		} else if (actionEvent.getSource() == three) {
			reason = 3;
			mbReason.setText("Meet a faculty");
		} else if (actionEvent.getSource() == four) {
			reason = 4;
			mbReason.setText("Request for locker");
		} else if (actionEvent.getSource() == five) {
			reason = 5;
			mbReason.setText("Chat with Kimberly");
		} else if (actionEvent.getSource() == six) {
			reason = 6;
			mbReason.setText("Complaints");
		} else if (actionEvent.getSource() == seven) {
			reason = 7;
			mbReason.setText("Ask questions (finance)");
		} else if (actionEvent.getSource() == eight) {
			reason = 8;
			mbReason.setText("Ask questions (IT)");
		} else if (actionEvent.getSource() == nine) {
			reason = 9;
			mbReason.setText("Ask questions (marketing)");
		} else if (actionEvent.getSource() == ten) {
			reason = 10;
			mbReason.setText("Ask questions (career service)");
		}

		if (actionEvent.getSource() == btnReport) {
			String timeStart = fromDate.getValue().toString();
			String timeEnd = toDate.getValue().toString();

			ObservableList<PieChart.Data> data1 = FXCollections.observableArrayList(
					new PieChart.Data("Ask for stapler", JDBC.NumberOfRecords(timeStart, timeEnd, 1, 'F')),
					new PieChart.Data("Hand in or collect assignment",
							JDBC.NumberOfRecords(timeStart, timeEnd, 2, 'F')),
					new PieChart.Data("Meet a faculty", JDBC.NumberOfRecords(timeStart, timeEnd, 3, 'F')),
					new PieChart.Data("Request for locker", JDBC.NumberOfRecords(timeStart, timeEnd, 4, 'F')),
					new PieChart.Data("Chat with Kimberly", JDBC.NumberOfRecords(timeStart, timeEnd, 5, 'F')),
					new PieChart.Data("Complaints", JDBC.NumberOfRecords(timeStart, timeEnd, 6, 'F')),
					new PieChart.Data("Ask questions (finance)", JDBC.NumberOfRecords(timeStart, timeEnd, 7, 'F')),
					new PieChart.Data("Ask questions (IT)", JDBC.NumberOfRecords(timeStart, timeEnd, 8, 'F')),
					new PieChart.Data("Ask questions (marketing)", JDBC.NumberOfRecords(timeStart, timeEnd, 9, 'F')),
					new PieChart.Data("Ask questions (career service)",
							JDBC.NumberOfRecords(timeStart, timeEnd, 10, 'F')));

			ObservableList<PieChart.Data> data2 = FXCollections.observableArrayList(
					new PieChart.Data("Ask for stapler", JDBC.NumberOfRecords(timeStart, timeEnd, 1, 'M')),
					new PieChart.Data("Hand in or collect assignment",
							JDBC.NumberOfRecords(timeStart, timeEnd, 2, 'M')),
					new PieChart.Data("Meet a faculty", JDBC.NumberOfRecords(timeStart, timeEnd, 3, 'M')),
					new PieChart.Data("Request for locker", JDBC.NumberOfRecords(timeStart, timeEnd, 4, 'M')),
					new PieChart.Data("Chat with Kimberly", JDBC.NumberOfRecords(timeStart, timeEnd, 5, 'M')),
					new PieChart.Data("Complaints", JDBC.NumberOfRecords(timeStart, timeEnd, 6, 'M')),
					new PieChart.Data("Ask questions (finance)", JDBC.NumberOfRecords(timeStart, timeEnd, 7, 'M')),
					new PieChart.Data("Ask questions (IT)", JDBC.NumberOfRecords(timeStart, timeEnd, 8, 'M')),
					new PieChart.Data("Ask questions (marketing)", JDBC.NumberOfRecords(timeStart, timeEnd, 9, 'M')),
					new PieChart.Data("Ask questions (career service)",
							JDBC.NumberOfRecords(timeStart, timeEnd, 10, 'M')));

			pie1.setData(data1);
			pie2.setData(data2);
		}
	}
}
