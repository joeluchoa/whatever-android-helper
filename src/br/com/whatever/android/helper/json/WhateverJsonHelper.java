package br.com.whatever.android.helper.json;

import br.com.whatever.android.helper.db.WhateverDbModel;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WhateverJsonHelper {

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
				b.registerTypeAdapter(cls,
						new WhateverDbModelJsonAdapter<WhateverDbModel>(cls));
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

}
