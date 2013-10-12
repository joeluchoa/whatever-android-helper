package br.com.whatever.android.helper.json;

import java.lang.reflect.Type;

import br.com.whatever.android.helper.db.WhateverDbModel;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class WhateverDbModelJsonAdapter<T extends WhateverDbModel> implements
		JsonSerializer<T>, JsonDeserializer<T> {

	private Class<?> mClass;

	public WhateverDbModelJsonAdapter(Class<?> cls) {
		mClass = cls;
	}

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

	public JsonElement serialize(T src, Type typeOfSrc,
			JsonSerializationContext context) {
		return src == null ? null : new JsonPrimitive(src.getId());
	}

}