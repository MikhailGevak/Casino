package newage.common.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


public class ServletParameters{
	public static final String SERVLET_PREFIX = "rest.servlet.property.";
	
	private Map<String, String> parameters = new HashMap<>();

	public ServletParameters(Properties properties) {
		for(Entry<Object, Object> entry : properties.entrySet()){
			if(entry.getKey().toString().startsWith(SERVLET_PREFIX)){
				String key = entry.getKey().toString().replace(SERVLET_PREFIX, "");
				parameters.put(key, entry.getValue().toString());
			}
		}
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

}
