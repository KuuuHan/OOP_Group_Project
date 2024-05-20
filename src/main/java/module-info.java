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
    exports ui.gp.Database;
    exports ui.gp.Models.Users;
    opens ui.gp to javafx.fxml;
    exports ui.gp.Tab;
    exports ui.gp.SceneController.Policy;
    opens ui.gp.SceneController.Policy to javafx.fxml;
    exports ui.gp.SceneController.Function;
    opens ui.gp.SceneController.Function to javafx.fxml;
    exports ui.gp.SceneController.Manager;
    opens ui.gp.SceneController.Manager to javafx.fxml;
    exports ui.gp.SceneController.Controllers;
    opens ui.gp.SceneController.Controllers to javafx.fxml;
    exports ui.gp.View;
    opens ui.gp.Tab to javafx.fxml;
    opens ui.gp.Models to javafx.base;

}