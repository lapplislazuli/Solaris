module Solaris {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    requires org.apache.logging.log4j;
    
    requires org.glassfish.java.json;
	
    exports logic;
}