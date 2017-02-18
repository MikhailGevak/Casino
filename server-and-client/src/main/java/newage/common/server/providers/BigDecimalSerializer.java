package newage.common.server.providers;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BigDecimalSerializer implements JsonSerializer<BigDecimal>, JsonDeserializer<BigDecimal>{

	@Override
	public JsonElement serialize(BigDecimal src, Type typeOfSrc, JsonSerializationContext context) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setGroupingUsed(true);

		return new JsonPrimitive(df.format(src));
	}

	@Override
	public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return json.getAsBigDecimal();
	}

}
