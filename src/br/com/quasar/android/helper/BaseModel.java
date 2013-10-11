package br.com.quasar.android.helper;

public class BaseModel {

	private int id;

	public BaseModel() {
	}

	public BaseModel(BaseModel model) {
		id = model.getId();
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId();
		result = prime
				* result
				+ ((this.getClass().getName() == null) ? 0 : (this.getClass()
						.getName().hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModel other = (BaseModel) obj;
		if (getId() != other.getId())
			return false;
		else if (!this.getClass().getName().equals(other.getClass().getName()))
			return false;
		return true;
	}

	public static boolean getBoolean(String str) {
		return Boolean.parseBoolean(str);
	}

}
