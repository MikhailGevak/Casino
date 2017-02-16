package newage.common.server.bind;

import java.util.Properties;

import com.google.inject.Binder;
import com.google.inject.Module;

import newage.common.api.properties.DatabaseProperties;
import newage.common.api.properties.PropertyService;
import newage.common.api.properties.ServerProperties;
import newage.common.impl.properties.DatabasePropertiesImpl;
import newage.common.impl.properties.PropertyServiceImpl;
import newage.common.impl.properties.ServerPropertiesImpl;

public class PropertiesModule implements Module {
	final private Properties properties;

	public PropertiesModule(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(DatabaseProperties.class).to(DatabasePropertiesImpl.class);
		binder.bind(PropertyService.class).toInstance(new PropertyServiceImpl(properties));
		binder.bind(ServerProperties.class).to(ServerPropertiesImpl.class);
	}
}
