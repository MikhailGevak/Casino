package newage.common.impl.properties;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import newage.common.api.properties.PropertyService;
import newage.common.api.properties.ServerProperties;

@Singleton
public class ServerPropertiesImpl implements ServerProperties {
	public static final String SERVER_PORT_PROPERTY = "rest.server.port";
	public static final String CONTEXT_PATH_PROPERTY = "rest.server.context_path";
	public static final String SERVER_HOST_NAME_PROPERTY = "rest.server.host_name";

	public static final String SERVER_PORT_DEFAULT = "9999";
	public static final String CONTEXT_PATH_DEFAULT = "";
	public static final String SERVER_HOST_NAME_DEFAULT = "http://127.0.0.1";

	private String port;
	private String contextPath;
	private String hostName;

	@Inject
	public ServerPropertiesImpl(PropertyService propertyService) {
		this(propertyService.getPropertyValue(SERVER_PORT_PROPERTY, SERVER_PORT_DEFAULT),
				propertyService.getPropertyValue(CONTEXT_PATH_PROPERTY, CONTEXT_PATH_DEFAULT),
				propertyService.getPropertyValue(SERVER_HOST_NAME_PROPERTY, SERVER_HOST_NAME_DEFAULT));
	}

	public ServerPropertiesImpl(String port, String contextPath, String hostName) {
		this.port = port;
		this.contextPath = contextPath;
		this.hostName = hostName;
	}

	@Override
	public String getPort() {
		return port;
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}

	@Override
	public String getHostName() {
		return hostName;
	}

	@Override
	public URI getServerURI() throws URISyntaxException {
		return new URI(getHostName() + ":" + getPort() + "/" + getContextPath());
	}
}
