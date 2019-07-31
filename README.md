# Setting up a Maven Project for Clojure-OSGi

## Declare maven-bundle-plugin

```xml
<plugin>
    <groupId>org.apache.felix</groupId>
    <artifactId>maven-bundle-plugin</artifactId>
    <version>4.2.0</version>
    <extensions>true</extensions>
    <configuration>
        <obrRepository>NONE</obrRepository>
        <instructions>
            <Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>
            <Clojure-Require>$BASE_PROJECT_NAMESPACE$</Clojure-Require>
            <Clojure-ActivatorNamespace>$BASE_PROJECT_NAMESPACE$</Clojure-ActivatorNamespace>
            <Import-Package>
                !sun.misc,
                clojure.*,
                *
            </Import-Package>
            <Export-Package />
            <DynamicImport-Package>*</DynamicImport-Package>
        </instructions>
    </configuration>
</plugin>
```

Clojure-OSGi requires a _special_ namespace to be required/declared which acts as an OSGi `Bundle-Activator`
and is the entry point for your project. This is where you will register any clojure based OSGi service implementations.

Replace the `$BASE_PROJECT_NAMESPACE$` entry above with the namespace, such as `com.example.activator`.

## Write the Bundle-Activator namespace

Define your namespace in a clojure file such as `src/main/clojure/com/example/activator.clj` containing the following:

```clojure
(ns com.example.activator
  (:import (com.example.api SomeJavaInterface))
  (:require [clojure.osgi.services :as os]))

(defn- bundle-start [context]
  "Register OSGi service"
  (try (os/register-service
         SomeJavaInterface
         (doSomething [_ arg] .......))
       (catch Exception e
         (println "Unable to register Service"))))

(defn- bundle-stop [context]
  (println "bundle-stop is called"))
```

When this namespace is included in your bundle jar file, either via the `clojure-maven-plugin` or just copying resources, when deployed
into an OSGi container, Clojure-OSGi will look detect the two Manifest headers: `Clojure-Require` and `Clojure-ActivatorNamespace` and
execute the appropriate commands to start/stop the bundle.

The call to the `register-service` macro takes a Java interface, reifies the macro body into an instance of the class, and registers it as a service.

The `clojure.osgi.services` namespace also contains additional support functions to lookup services in the running container.
