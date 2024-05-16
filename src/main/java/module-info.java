module ui.gp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    exports ui.gp;
    exports ui.gp.SceneController;
    exports ui.gp.SceneController.Customer;
    exports ui.gp.SceneController.Customer.Dependent;
    exports ui.gp.Utils;
    exports ui.gp.Models.Users;

    opens ui.gp to javafx.fxml;
    opens ui.gp.SceneController to javafx.fxml;
    opens ui.gp.SceneController.Customer to javafx.fxml;
    opens ui.gp.SceneController.Customer.Dependent to javafx.fxml;
}