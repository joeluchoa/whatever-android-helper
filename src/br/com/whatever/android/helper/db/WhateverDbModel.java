package br.com.whatever.android.helper.db;

public class WhateverDbModel {

	public WhateverDbModel() {
	}

	public Integer getId() {
		return 0;
	}

	public void setId(Integer id) {
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
		WhateverDbModel other = (WhateverDbModel) obj;
		if (getId() != other.getId())
			return false;
		else if (!this.getClass().getName().equals(other.getClass().getName()))
			return false;
		return true;
	}
}
