package newage.common.impl.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Singleton;

import newage.common.api.properties.PropertyService;

@Singleton
public class PropertyServiceImpl implements PropertyService {
	private Properties properties;

	public PropertyServiceImpl() {
		load(new Properties());
	}

	public PropertyServiceImpl(InputStream stream) throws IOException {
		Properties properties = new Properties();
		properties.load(stream);
		load(properties);
	}

	public PropertyServiceImpl(Properties properties) {
		load(properties);
	}

	public void load(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String getPropertyValue(String name) {
		return System.getProperty(name, properties.getProperty(name));
	}
}
