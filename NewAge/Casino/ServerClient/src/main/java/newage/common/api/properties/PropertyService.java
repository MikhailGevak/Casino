package newage.common.api.properties;

public interface PropertyService {
	String getPropertyValue(String name);

	default String getPropertyValue(String name, String defaultValue) {
		String value = getPropertyValue(name);
		if (value == null) {
			value = defaultValue;
		}
		
		return value;
	}
}
