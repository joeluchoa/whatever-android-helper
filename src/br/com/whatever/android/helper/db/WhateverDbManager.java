package br.com.whatever.android.helper.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class WhateverDbManager {

	public static WhateverDbHelper getDbHelper(Context context,
			Class<? extends WhateverDbHelper> helperClass) {
		return OpenHelperManager.getHelper(context, helperClass);
	}

	public static void releaseDbHelper() {
		OpenHelperManager.releaseHelper();
	}
}
