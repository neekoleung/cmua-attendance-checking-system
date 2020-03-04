package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JDBC;
import model.Report1;

import java.io.IOException;
import java.util.List;

/**
 * @author Neeko
 *
 */
public class TableReportController {
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
	private TableView tbReport;

	@FXML
	private TableColumn name;

	@FXML
	private TableColumn date;

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
			int category = reason;

			ObservableList<Report1> list = FXCollections.observableArrayList();

			// Get the resultset from the filterChooser.
			List<Report1> report = JDBC.ReportByCategories(timeStart, timeEnd, category);

			System.out.println("Printing data for table");
			for (Report1 record : report) {
				System.out.println(record.getName() + " " + record.getDate());
			}

			// Build the mapping between object fields and table cells.
			name.setCellValueFactory(new PropertyValueFactory<Report1, String>("name"));
			date.setCellValueFactory(new PropertyValueFactory<Report1, String>("date"));

			// Add the staffs to the observable list.
			for (int i = 0; i < report.size(); i++) {
				list.add(report.get(i));
			}

			// Add the list of Staff to the tableview.
			tbReport.setItems(list);
		}

	}

}
