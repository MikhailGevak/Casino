package newage.common.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;

public class RestServer {
	public static final String DEFAULT_PROPERTIES_RESOURCE = "default.properties";

	protected RestServer(Collection<Module> modules, Properties defaultProperties) throws Exception {

		Properties properties = loadProperties(defaultProperties);

		ExtendedGuiceServletContextListener guiceServlet = new ExtendedGuiceServletContextListener(properties, modules);

		Server appServer = createSimpleJettyServer(guiceServlet);

		appServer.start();

		System.out.println("Server running");
		System.out.println("Hit return to stop...");
		System.in.read();
		System.out.println("Stopping server");
		appServer.stop();
		System.out.println("Server stopped");
	}

	private Properties loadProperties(Properties defaultProperties) throws IOException {
		Properties properties = servletDefaultProperties(defaultProperties);

		try {
			InputStream defaultStream = ClassLoader.getSystemClassLoader()
					.getResourceAsStream(DEFAULT_PROPERTIES_RESOURCE);
			if (defaultStream != null) {
				properties.load(defaultStream);
			}
		} catch (IOException ex) {
			System.out.println("Can't load properties from the " + DEFAULT_PROPERTIES_RESOURCE);
		}

		setAdditionProperties(properties);
		
		return properties;
	}

	private void setAdditionProperties(Properties properties) {
		String providersProperty = ServletParameters.SERVLET_PREFIX + "com.sun.jersey.config.property.packages";
		String providers = properties.getProperty(providersProperty, "");

		providers = "newage.common.server" + (StringUtils.isNotEmpty(providers) ? (", " + providers): "");
		
		properties.setProperty(providersProperty, providers);
	}

	private Properties servletDefaultProperties(Properties defaultProperties) {
		Properties properties = new Properties();
		properties.setProperty(
				ServletParameters.SERVLET_PREFIX + "com.sun.jersey.spi.container.ContainerRequestFilters",
				"com.sun.jersey.api.container.filter.LoggingFilter");
		properties.setProperty(
				ServletParameters.SERVLET_PREFIX + "com.sun.jersey.spi.container.ContainerResponseFilters",
				"com.sun.jersey.api.container.filter.LoggingFilter");
		properties.setProperty(ServletParameters.SERVLET_PREFIX + "com.sun.jersey.config.feature.Trace", "true");

		properties.putAll(defaultProperties);
		return properties;
	}

	private Server createSimpleJettyServer(ExtendedGuiceServletContextListener guiceConfig) throws URISyntaxException {
		URI uri = guiceConfig.getServerURI();
		Server server = new Server(uri.getPort());

		ServletContextHandler root = new ServletContextHandler(server, uri.getPath());

		root.addEventListener(guiceConfig);
		root.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

		root.addServlet(DummyServlet.class, "/*");

		return server;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Collection<Module> modules = new ArrayList<>();
		private Collection<String> args = new ArrayList<>();

		public Builder addModules(Collection<Module> modules) {
			this.modules.addAll(modules);
			return this;
		}

		public Builder addArgs(Collection<String> args) {
			this.args.addAll(args);
			return this;
		}

		public Builder addModule(Module module) {
			this.modules.add(module);
			return this;
		}

		public Builder addArg(String arg) {
			this.args.add(arg);
			return this;
		}
		
		public RestServer runServer(Properties defaultProperties) throws Exception {
			return new RestServer(modules, defaultProperties);
		}
	}
}
