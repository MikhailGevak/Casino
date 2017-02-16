package newage.wallet;

import java.io.IOException;
import java.util.Properties;

import com.google.inject.Injector;

import newage.common.impl.properties.DatabasePropertiesImpl;
import newage.common.server.ExtendedGuiceServletContextListener;

public class TestUtils {
	public static Injector getTestInjector() throws IOException {

		Properties properties = new Properties();
		properties.put(DatabasePropertiesImpl.SERVER_DATABASE_PROPERTY, "jdbc:sqlite::memory:");
		properties.put(DatabasePropertiesImpl.DATABASE_DRIVER_PROPERTY, "org.sqlite.JDBC");
		
		ExtendedGuiceServletContextListener context = new ExtendedGuiceServletContextListener(properties, WalletServer.getModules());
		
		return context.getInjector();
	}
}
