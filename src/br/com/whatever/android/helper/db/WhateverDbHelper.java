package br.com.whatever.android.helper.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.whatever.android.helper.json.WhateverJsonHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class WhateverDbHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = WhateverDbHelper.class.getSimpleName();

	private Class<?>[] dbTables = null;
	private WhateverJsonHelper jsonHelper;

	public WhateverDbHelper(Context context, String dbName, int dbVersion,
			Class<?>[] dbTables) {
		super(context, dbName, null, dbVersion);
		this.dbTables = dbTables;
		this.jsonHelper = new WhateverJsonHelper();
		this.jsonHelper.setAdapterforModels(dbTables);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		createAllTables(connectionSource);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		resetDatabase(connectionSource);
	}

	public void resetDatabase(ConnectionSource connectionSource) {
		dropAllTables(connectionSource);
		createAllTables(connectionSource);
	}

	private void createAllTables(ConnectionSource connectionSource) {
		try {
			for (Class<?> table : dbTables) {
				TableUtils.createTable(connectionSource, table);
			}
		} catch (java.sql.SQLException e) {
			Log.e(TAG, "Can't create database", e);
		}
	}

	private void dropAllTables(ConnectionSource connectionSource) {
		try {
			for (Class<?> table : dbTables) {
				TableUtils.dropTable(connectionSource, table, true);
			}
		} catch (SQLException e) {
			Log.e(TAG, "Can't upgrade database", e);
		}
	}

	public <D extends Dao<T, ?>, T> D getModelDao(Class<T> cls) {
		try {
			return getDao(cls);
		} catch (SQLException e) {
			Log.e(TAG, "Error to get DAO for class " + cls.getSimpleName(), e);
		}
		return null;
	}

	public WhateverJsonHelper getJsonHelper() {
		return jsonHelper;
	}
}
