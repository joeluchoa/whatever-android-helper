package br.com.quasar.android.helper.db;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.quasar.android.helper.json.QuasarJsonHelper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class BaseService<T extends BaseModel> {

	private Dao<T, Integer> dao;
	private Class<T> cls;
	private QuasarJsonHelper jsonHelper;

	public BaseService(DatabaseHelper dbHelper, Class<T> cls) {
		this.cls = cls;
		this.dao = dbHelper.getModelDao(cls);
		this.jsonHelper = dbHelper.getJsonHelper();
	}

	public CreateOrUpdateStatus save(String json) {
		try {
			T model = jsonHelper.build(cls).fromJson(json, cls);
			return save(model);
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
		return null;
	}

	public Set<Integer> getAll(String... columns) {
		Set<Integer> ids = new HashSet<Integer>();
		try {
			QueryBuilder<T, Integer> b = dao.queryBuilder();
			PreparedQuery<T> q = b.selectColumns(columns).prepare();
			List<T> list = dao.query(q);
			for (T m : list) {
				ids.add(m.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public Dao<T, Integer> getDao() {
		return dao;
	}
}
