package config.interfaces;

import org.pmw.tinylog.Level;

public interface LoggerSettings {

	Level getLevel();

	void setLevel(Level level);

	void setLevel(String level);

	String getLogfile();

	void setLogfile(String logfile);

	boolean isAppend();

	void setAppend(boolean append);

}