package com.esprit.espritevent;

import com.esprit.espritevent.Models.Model;
import javafx.application.Application;

import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactroy().showLoginWindow();
    }



}
