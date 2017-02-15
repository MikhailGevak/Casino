package newage.common.exception;
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ErrorResponseSerializer implements JsonSerializer<ErrorResponse> {
	@Override
	public JsonElement serialize(ErrorResponse error, Type type, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(ErrorResponse.ERROR_CODE, error.errorCode);
		jsonObject.addProperty(ErrorResponse.ERROR_MESSAGE, error.errorMessage);
		jsonObject.addProperty(ErrorResponse.ERROR_CLASS, error.errorClassName);

		return jsonObject;
	}
}
