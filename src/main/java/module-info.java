module Solaris {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    requires org.json;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    
    opens org.openjfx to javafx.fxml;
    exports logic;
}