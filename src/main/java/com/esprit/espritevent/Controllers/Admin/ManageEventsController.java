package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.CustomMapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.fxml.Initializable;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageEventsController implements Initializable {
    private final MapPoint espritCharguia = new MapPoint(36.85328920224959,10.20719057853217);
    public VBox vbox_map_fid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MapView mapView = createMapView();
        vbox_map_fid.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
    }
    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(500, 400);
        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);
        mapView.flyTo(0,espritCharguia,0.1);

        return mapView;
    }
}
