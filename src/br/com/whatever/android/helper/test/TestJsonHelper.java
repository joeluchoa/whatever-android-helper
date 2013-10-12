package br.com.whatever.android.helper.test;

import br.com.whatever.android.helper.db.WhateverDbModel;
import br.com.whatever.android.helper.json.WhateverJsonHelper;

import com.google.gson.annotations.SerializedName;

public class TestJsonHelper {

	public static class A extends WhateverDbModel {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class B extends WhateverDbModel {
		private Boolean ok;
		@SerializedName("LocalInspecaoId")
		private A a;

		public Boolean isOk() {
			return ok;
		}

		public void setOk(Boolean ok) {
			this.ok = ok;
		}

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}

		public Boolean getOk() {
			return ok;
		}
	}

	public static void main(String[] args) {
		A a = new A();
		a.setId(1);
		a.setName("Joel");
		B b = new B();
		b.setA(a);
		b.setId(2);
		b.setOk(true);

		Class<?>[] models = { A.class, B.class };

		WhateverJsonHelper helper = new WhateverJsonHelper();

		helper.setAdapterforModels(models);

		B bb = helper.build(B.class).fromJson(helper.build(B.class).toJson(b),
				B.class);

		System.out.println(helper.build(A.class).toJson(a));
		System.out.println(helper.build(B.class).toJson(b));
		System.out.println(helper.build(B.class).toJson(bb));
	}
}
