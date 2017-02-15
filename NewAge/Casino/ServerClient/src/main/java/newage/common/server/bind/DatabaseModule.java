package newage.common.server.bind;

import java.sql.SQLException;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import newage.common.api.properties.DatabaseProperties;


public class DatabaseModule implements Module{

	@Override
	public void configure(Binder binder) {
		binder.bind(ConnectionSource.class).to(ConnectionSourceImpl.class);
	}

	@Singleton
	public static class ConnectionSourceImpl extends JdbcConnectionSource {
		@Inject
		public ConnectionSourceImpl(DatabaseProperties databaseProperties) throws SQLException, ClassNotFoundException {
			super(databaseProperties.getDatabaseURI());
			Class.forName(databaseProperties.getDriverClassName());
		}
	}

}
