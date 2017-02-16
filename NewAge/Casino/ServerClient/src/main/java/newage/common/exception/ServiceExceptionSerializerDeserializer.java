package newage.common.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ErrorResponseDeserializer implements JsonDeserializer<ServiceException> {
	@Override
	public ServiceException deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (!json.isJsonObject())
			throw new JsonParseException(json.toString() + " is not a json object!");
		JsonObject jsonObject = json.getAsJsonObject();

		String errorMessage = jsonObject.get(ErrorResponse.ERROR_MESSAGE).getAsString();
		String errorClass = jsonObject.get(ErrorResponse.ERROR_CLASS).getAsString();

		Class<?> clazz;

		try {
			clazz = Class.forName(errorClass);
			Constructor<?> constructor = clazz.getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			return (ServiceException) constructor.newInstance(errorMessage);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException | NoSuchMethodException e) {
			throw new JsonParseException(e);
		}
	}
}