package newage.common.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ServiceExceptionSerializerDeserializer implements JsonDeserializer<ServiceException>, JsonSerializer<ServiceException> {
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String ERROR_CLASS = "errorClass";
	
	@Override
	public ServiceException deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (!json.isJsonObject())
			throw new JsonParseException(json.toString() + " is not a json object!");
		JsonObject jsonObject = json.getAsJsonObject();

		String errorMessage = jsonObject.get(ERROR_MESSAGE).getAsString();
		String errorClass = jsonObject.get(ERROR_CLASS).getAsString();
		Integer errorCode = jsonObject.get(ERROR_CODE).getAsInt();
		Class<?> clazz;

		try {
			clazz = Class.forName(errorClass);
			
			Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Integer.class);
			constructor.setAccessible(true);
			return (ServiceException) constructor.newInstance(errorMessage, errorCode);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException | NoSuchMethodException e) {
			throw new JsonParseException(e);
		}
	}
	
	@Override
	public JsonElement serialize(ServiceException error, Type type, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(ERROR_CODE, error.getErrorCode());
		jsonObject.addProperty(ERROR_MESSAGE, error.getMessage());
		jsonObject.addProperty(ERROR_CLASS, error.getClass().getName());

		return jsonObject;
	}
}