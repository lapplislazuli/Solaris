package config.interfaces;

import org.apache.logging.log4j.Level;

public interface LoggerSettings {

	Level getLevel();

	void setLevel(Level level);

	void setLevel(String level);

	String getLogfile();

	void setLogfile(String logfile);

	boolean isAppend();

	void setAppend(boolean append);

}