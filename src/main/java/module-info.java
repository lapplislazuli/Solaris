module Solaris {
    requires javafx.controls;
    requires transitive javafx.graphics;
    
    requires org.json;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    
    exports logic;
}