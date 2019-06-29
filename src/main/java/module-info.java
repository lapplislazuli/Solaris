module Solaris {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    requires org.apache.logging.log4j;
    
    requires org.json;
	
    exports logic;
}