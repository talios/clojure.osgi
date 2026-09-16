package clojure.osgi.internal;

import java.io.PrintStream;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class StreamLog implements LogService {

	private PrintStream stream;

	public StreamLog(final PrintStream out) {
		this.stream = out;
	}

	public void log(final int severity, final String message) {
		stream.println(String.format("%d - %s", severity, message));
	}

	public void log(final int severity, final String message, final Throwable exception) {
		log(severity, message);
		exception.printStackTrace(stream);
	}

	public void log(final ServiceReference service, final int severity, final String message) {
	}

	public void log(final ServiceReference service, final int severity, final String message, final Throwable exception) {
	}

}
