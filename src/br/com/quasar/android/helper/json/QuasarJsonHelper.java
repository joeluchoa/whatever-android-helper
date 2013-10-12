package br.com.quasar.android.helper.json;

import java.lang.reflect.Type;

import br.com.quasar.android.helper.db.BaseModel;

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

	private Class<?>[] models;

	public GsonBuilder getBuilder() {
		return getBuilder(null);
	}

	public GsonBuilder getBuilder(Class<?> toExclude) {
		GsonBuilder b = new GsonBuilder();
		b.setDateFormat(DEFAULT_DATE_FORMAT);
		b.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		for (Class<?> cls : models) {
			if (!cls.equals(toExclude)) {
				b.registerTypeAdapter(cls, new ModelAdapter<BaseModel>(cls));
			}
		}
		return b.serializeNulls();
	}

	public Gson build() {
		return build(null);
	}

	public Gson build(Class<?> toExclude) {
		return getBuilder(toExclude).create();
	}

	public void setAdapterforModels(Class<?>[] models) {
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
}
