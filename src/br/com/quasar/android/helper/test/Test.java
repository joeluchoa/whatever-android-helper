package br.com.quasar.android.helper.test;

import br.com.quasar.android.helper.BaseModel;
import br.com.quasar.android.helper.QuasarJsonHelper;

import com.google.gson.annotations.SerializedName;

public class Test {

	static class A extends BaseModel {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static class B extends BaseModel {
		Boolean ok;
		@SerializedName("LocalInspecaoId")
		A a;

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

		Class<?>[] models = { A.class, B.class };

		QuasarJsonHelper helper = QuasarJsonHelper.getInstance();

		helper.setAdapterforModel(models);

		System.out.println(helper.build(A.class).toJson(a));
		System.out.println(helper.build(B.class).toJson(b));
	}
}
