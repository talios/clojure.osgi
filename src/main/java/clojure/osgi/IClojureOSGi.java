package clojure.osgi;

import org.osgi.framework.Bundle;

public interface IClojureOSGi {
	void unload(Bundle aBundle);
	void require(Bundle aBundle, String aName);
	void loadAOTClass(Bundle aContext, String fullyQualifiedAOTClassName) throws Exception;
	Object withBundle(Bundle aBundle, RunnableWithException aCode);
}
