package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Model;
import com.esprit.espritevent.Services.Club.ServiceClub;
import com.esprit.espritevent.Services.Local.ServiceLocal;
import javafx.fxml.Initializable;
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
    ServiceClub serviceClub = new ServiceClub();
    ServiceLocal serviceLocal = new ServiceLocal();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        current_date_fid.setText(currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        user_name_fid.setText(Model.getInstance().getUser().getUsername());

        try {
            count_clubs_fid.setText(String.valueOf(serviceClub.countClubs()));
            count_locals_fid.setText(String.valueOf(serviceLocal.countLocals()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
