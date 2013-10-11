package br.com.quasar.android.helper;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuasarJsonHelper {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

	private static QuasarJsonHelper sInstance;

	private GsonBuilder builder;

	private QuasarJsonHelper() {
		builder = new GsonBuilder();

		builder.setDateFormat(DEFAULT_DATE_FORMAT);

		// TODO
	}

	public static QuasarJsonHelper getInstance() {
		if (sInstance == null) {
			sInstance = new QuasarJsonHelper();
		}
		return sInstance;
	}

	public Gson build() {
		return builder.create();
	}

	JsonSerializer<BaseModel> ser = new JsonSerializer<BaseModel>() {

		@Override
		public JsonElement serialize(BaseModel src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getId());
		}
	};

	JsonDeserializer<BaseModel> deser = new JsonDeserializer<BaseModel>() {
		@Override
		public BaseModel deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			// TODO get Class<?>
			return json == null ? null : null;
		}
	};

}
