package telegrambot.tgcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class JsonUtils {

	@FunctionalInterface
	public interface ConstructorII <C extends TGobject> {
		C create(JSONObject par);
	}


	// TODO test !!
	public static <C extends TGobject> ArrayList<ArrayList<C>> optArrayListOfArrayList(ConstructorII<C> c, JSONArray arr) {

		ArrayList<ArrayList<C>> aa = new ArrayList<>();
		int size;

		if (arr == null || (size = arr.length()) <= 0)
			return null;

		for (int i = 0; i < size; i++)
			aa.add(getArrayList(c, arr.getJSONArray(i)));

		return aa;
	}

	// use only optJSONArray(), never getJSONArray with this function, No Wrapper type
	public static <C extends TGobject> ArrayList<C> optArrayList(ConstructorII<C> c, JSONArray arr) {

		ArrayList<C> al = new ArrayList<>();
		int size;

		if (arr == null || (size = arr.length()) <= 0)
			return null;

		for (int i = 0; i < size; i++)
			al.add(c.create(arr.getJSONObject(i)));

		return al;
	}

	// yes it's code dup... i don't care
	public static <C extends TGobject> Queue<C> optQueue(ConstructorII<C> c, JSONArray arr) {

		Queue<C> al = new LinkedList<>();
		int size;

		if (arr == null || (size = arr.length()) <= 0)
			return null;

		for (int i = 0; i < size; i++)
			al.add(c.create(arr.getJSONObject(i)));

		return al;
	}


	public static <C extends TGobject> ArrayList<C> getArrayList(ConstructorII<C> c, JSONArray arr) {
		ArrayList<C> a = optArrayList(c, arr);
		if (a == null) throw new SelfInitException("required field not found");
		return a;
	}

	public static <C extends TGobject> void reflectInit(C obj, String field_name, boolean isOpt) {

		try {

			// getting class
			Class<? extends TGobject> clazz = obj.getClass();

			// getting field by name
			Field field = clazz.getDeclaredField(field_name);
			field.setAccessible(true);

			// get the json representation of object
			JSONObject json = obj.json;

			// get the type of field_name
			Class<?> cl = field.getType();

			if (TGobject.class.isAssignableFrom(cl)) {

				JSONObject value;

				try { value = json.getJSONObject(field_name); } 
				catch (JSONException e) 
				{ if (isOpt) value = null; else throw new SelfInitException(cl, field); }

				if (value != null) {
					Constructor<?> constr = cl.getDeclaredConstructor(JSONObject.class); 
					constr.setAccessible(true);
					field.set(obj, constr.newInstance(value));
				} else {
					field.set(obj, null);
				}

			} else if (cl == String.class) {

				String value;

				try { value = json.getString(field_name); } 
				catch (JSONException e) 
				{ if (isOpt) value = null; else throw new SelfInitException(cl, field); }

				field.set(obj, value);

			} else if (cl == Long.class) {

				// we must return null, not 0, 0.0 or false if those value are not found while parsing json, we use wrapper to avoid those problem
				Long value;

				try { value = json.getLong(field_name); } 
				catch (JSONException e) 
				{ if (isOpt) value = null; else throw new SelfInitException(cl, field); }

				field.set(obj, value);

			} else if (cl == Boolean.class) {

				Boolean value;

				try { value = json.getBoolean(field_name); } 
				catch (JSONException e) 
				{ if (isOpt) value = null; else throw new SelfInitException("Missing required field: "+cl); } 

				field.set(obj, value);

			} else if (cl == Double.class) {

				Double value;

				try { value = json.getDouble(field_name); } 
				catch (JSONException e) 
				{ if (isOpt) value = null; else throw new SelfInitException(cl, field); }

				field.set(obj, value);

			} else {
				throw new IllegalArgumentException("\nreflectInit(): wrong arguments\n");
			}

		} catch (Exception e) {
			throw new SelfInitException(e);
		}
	}
}
