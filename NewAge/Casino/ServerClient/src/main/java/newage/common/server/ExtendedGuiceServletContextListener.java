package newage.common.server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

import newage.common.api.properties.ServerProperties;
import newage.common.server.bind.GuiceBinderModule;
import newage.common.server.bind.PropertiesModule;

public class ExtendedGuiceServletContextListener extends GuiceServletContextListener {
	private Injector injector;
	private final Collection<Module> modules;

	public ExtendedGuiceServletContextListener(Properties properties, Collection<Module> additionalModules){
		modules = new ArrayList<>(additionalModules);
		modules.add(new GuiceBinderModule(properties));
		modules.add(new PropertiesModule(properties));
	}

	@Override
	public Injector getInjector() {
		if (injector == null) {
			try {
				injector = createInjector();
			} catch (SQLException | IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return injector;
	}

	protected Injector createInjector() throws SQLException, IOException, ClassNotFoundException {
		
		Injector injector = Guice.createInjector(modules);

		return injector;
	}

	public URI getServerURI() throws URISyntaxException {
		return getInjector().getInstance(ServerProperties.class).getServerURI();
	}
}