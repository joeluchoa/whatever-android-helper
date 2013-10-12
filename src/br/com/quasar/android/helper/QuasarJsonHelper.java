package br.com.quasar.android.helper;

import java.lang.reflect.Type;

import com.google.gson.FieldNamingPolicy;
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

	private Class<?>[] models;

	private QuasarJsonHelper() {
	}

	public static QuasarJsonHelper getInstance() {
		if (sInstance == null) {
			sInstance = new QuasarJsonHelper();
		}
		return sInstance;
	}

	public Gson build() {
		return build(null);
	}

	public Gson build(Class<?> toExclude) {
		GsonBuilder b = new GsonBuilder();
		b.setDateFormat(DEFAULT_DATE_FORMAT);
		b.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		for (Class<?> cls : models) {
			if (!cls.equals(toExclude)) {
				b.registerTypeAdapter(cls, new ModelAdapter<BaseModel>(cls));
			}
		}
		return b.serializeNulls().create();
	}

	public void setAdapterforModel(Class<?>[] models) {
		this.models = models;
	}

	static class ModelAdapter<T extends BaseModel> implements
			JsonSerializer<T>, JsonDeserializer<T> {

		private Class<?> mClass;

		public ModelAdapter(Class<?> cls) {
			mClass = cls;
		}

		@Override
		public T deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			if (json == null) {
				return null;
			}

			try {
				@SuppressWarnings("unchecked")
				T result = (T) mClass.newInstance();
				result.setId(json.getAsInt());
				return result;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public JsonElement serialize(T src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getId());
		}

	}

	static class BooleanAdapter implements JsonSerializer<Boolean>,
			JsonDeserializer<Boolean> {

		@Override
		public Boolean deserialize(JsonElement src, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return Boolean.parseBoolean(src.getAsString());
		}

		@Override
		public JsonElement serialize(Boolean src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.booleanValue());
		}

	}
}
