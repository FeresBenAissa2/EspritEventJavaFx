package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Local;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ManageClubController {
    public TextField club_email_fid;
    public TextField club_name_fid;
    public TextField club_description_fid;
    public DatePicker founding_date_fid;
    public TextField club_state_fid;
    public Button save_btn_fid;
    public Button update_btn_fid;
    public Button delete_btn_fid;
    public TableView<Local> club_table_view_fid;
    public TableColumn<Local,Long> column_club_id_fid;
    public TableColumn<Local,String> column_club_name_fid;
    public TableColumn<Local,String>  column_club_description_fid;
    public TableColumn<Local, LocalDate> column_founding_date_fid;
    public TableColumn<Local, String> column_club_email_fid;
    public TableColumn<Local, String> column_president_name_fid;
}
