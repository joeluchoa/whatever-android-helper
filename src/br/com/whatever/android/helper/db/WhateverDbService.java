package br.com.whatever.android.helper.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.whatever.android.helper.json.WhateverJsonHelper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class WhateverDbService<T extends WhateverDbModel> {

	private Dao<T, Integer> dao;
	private Class<T> cls;
	private WhateverJsonHelper jsonHelper;

	public WhateverDbService(WhateverDbHelper dbHelper, Class<T> cls) {
		this.cls = cls;
		this.dao = dbHelper.getModelDao(cls);
		this.jsonHelper = dbHelper.getJsonHelper();
	}

	public T save(String json) {
		try {
			T model = jsonHelper.build(cls).fromJson(json, cls);
			save(model);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public CreateOrUpdateStatus save(T model) {
		try {
			return dao.createOrUpdate(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void refresh(T model) {
		try {
			dao.refresh(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int remove(T model) {
		try {
			return dao.delete(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public T findById(Integer id) {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<T> getAll() {
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<T>();
	}

	public List<T> getAll(String... columns) {
		List<T> list = new ArrayList<T>();
		try {
			QueryBuilder<T, Integer> b = dao.queryBuilder();
			PreparedQuery<T> q = b.selectColumns(columns).prepare();
			list = dao.query(q);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<T> findBy(String column, Object value) {
		List<T> list = new ArrayList<T>();
		try {
			list = dao.queryForEq(column, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void removeAll() {
		try {
			for (T m : getAll()) {
				dao.delete(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Dao<T, Integer> getDao() {
		return dao;
	}
}
