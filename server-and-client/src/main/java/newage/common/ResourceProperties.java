package newage.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceProperties extends Properties{
	private static final long serialVersionUID = 1L;

	public ResourceProperties(String resourceName) throws Exception{
		super();
		InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);

		if (inputStream != null) {
			load(inputStream);
		}

		if (inputStream != null) {
			try {
				load(inputStream);
			} catch (IOException ex) {
				throw new Exception("Can't load resource " + resourceName);
			}
		}
	}
}
