package newage.game;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import com.google.inject.Injector;
import com.google.inject.Module;

import newage.common.impl.properties.DatabasePropertiesImpl;
import newage.common.server.ExtendedGuiceServletContextListener;

public class TestUtils {
	public static Injector getTestInjector() throws IOException {

		Properties properties = new Properties();
		properties.put(DatabasePropertiesImpl.SERVER_DATABASE_PROPERTY, "jdbc:sqlite::memory:");
		properties.put(DatabasePropertiesImpl.DATABASE_DRIVER_PROPERTY, "org.sqlite.JDBC");
		
		Collection<Module> modules = new LinkedList<>(GameServer.getModules());
		modules.add(new MockOtherServiceBinder());
		
		ExtendedGuiceServletContextListener context = new ExtendedGuiceServletContextListener(properties, modules);
		
		return context.getInjector();
	}
}
