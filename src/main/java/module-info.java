open module AM02 {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires java.logging;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires commons.io;
    exports it.polimi.ingsw.client.view.gui to javafx.graphics;
    exports it.polimi.ingsw.network to com.fasterxml.jackson.databind;


    // export javafx.application.Application implementation's package
    // to at least javafx.graphics
}
