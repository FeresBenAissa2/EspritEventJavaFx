package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Model;
import com.esprit.espritevent.Services.Club.ServiceClub;
import com.esprit.espritevent.Services.Event.ServiceEvent;
import com.esprit.espritevent.Services.Local.ServiceLocal;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name_fid;
    public Label current_date_fid;
    public Label count_clubs_fid;
    public Label count_events_fid;
    public Label count_locals_fid;
    @FXML
    public PieChart pie_chart_fid;
    ServiceClub serviceClub = new ServiceClub();
    ServiceLocal serviceLocal = new ServiceLocal();
    ServiceEvent serviceEvent = new ServiceEvent();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        current_date_fid.setText(currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        user_name_fid.setText(Model.getInstance().getUser().getPrenom() + " " + Model.getInstance().getUser().getNom());
        int approuvedClub =0;
        int refusedClub =0;
        int InProgressClub =0;
        try {
            count_clubs_fid.setText(String.valueOf(serviceClub.countClubs()));
            count_locals_fid.setText(String.valueOf(serviceLocal.countLocals()));
            count_events_fid.setText(String.valueOf(serviceEvent.countEvent()));
            approuvedClub =   serviceClub.getAcceptedClubsCount();
            refusedClub =   serviceClub.getRefusedClubsCount();
            InProgressClub =   serviceClub.getInProgressClubsCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Approuved", approuvedClub),
                new PieChart.Data("Refused", refusedClub),
                new PieChart.Data("In Progress", InProgressClub)
        );
        pie_chart_fid.getData().addAll(pieChartData);

    }
}
