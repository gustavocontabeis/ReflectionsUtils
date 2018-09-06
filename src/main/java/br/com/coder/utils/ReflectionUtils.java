/**
 * 
 */
package br.com.coder.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

public class ReflectionUtils {

	public static void printGettersSetters(Class aClass) {
		Method[] methods = aClass.getMethods();
		for (Method method : methods) {
			if (isGetter(method))
				System.out.println("getter: " + method);
			if (isSetter(method))
				System.out.println("setter: " + method);
		}
	}

	public static boolean isGetter(Method method) {
		if (!method.getName().startsWith("get")) {
			return false;
		}
		if (method.getParameterTypes().length != 0) {
			return false;
		}
		if (void.class.equals(method.getReturnType())) {
			return false;
		}
		return true;
	}

	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set")) {
			return false;
		}
		if (method.getParameterTypes().length != 1) {
			return false;
		}
		return true;
	}

	/**
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj1
	 * @param string
	 * @return
	 */
	public static Field getField(Object obj1, String string) {
		Class classe = obj1.getClass();
		do {
			Field[] declaredFields = classe.getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.getName().equals(string)) {
					return field;
				}
			}
			classe = classe.getSuperclass();
		} while (classe != Object.class);
		return null;
	}

	public static Field[] getFields(Class classe) {
		List<Field> list = new ArrayList<Field>();
		do {
			Field[] declaredFields = classe.getDeclaredFields();
			for (Field field : declaredFields) {
				list.add(field);
			}
			classe = classe.getSuperclass();
		} while (classe != Object.class);
		return null;
	}

	public static Field[] getFields(Object obj) {
		return getFields(obj.getClass());
	}

	@Deprecated
	public static Annotation[] getAnnotations(Object obj, String name) {
		List<Annotation> list = new ArrayList<Annotation>();
		Field field = getField(obj, name);
		for( Annotation a : field.getDeclaredAnnotations()){
			list.add(a);
		}
		Method getter = getGetter(obj.getClass(), field);//XXXXXXXXXXXXXXXXXXX
		for(Annotation a : getter.getDeclaredAnnotations()){
			list.add(a);
		}
		Method setter = getSetter(obj.getClass(), field);//XXXXXXXXXXXXXXXXXXX
		for(Annotation a : setter.getDeclaredAnnotations()){
			list.add(a);
		}
		return list.toArray(new Annotation[list.size()]);
	}

	/**
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj
	 * @param field
	 * @return
	 */
	private static Method getSetter(Class classe, Field field) {
		String seterName = getSeterName(field.getName());
		Method[] methods = getMethods(classe);
		for (Method method : methods) {
			if(seterName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	/**
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj
	 * @param field
	 * @return
	 */
	private static Method getGetter(Class obj, Field field) {
		String geterName = getGeterName(field.getName());
		Method[] methods = getMethods(obj);
		for (Method method : methods) {
			if(geterName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	public static Method getMethod(Class classe, String name) {
		do {
			Method[] methodFields = classe.getDeclaredMethods();
			for (Method method : methodFields) {
				if (method.getName().equals(name)) {
					return method;
				}
			}
			classe = classe.getSuperclass();
		} while (classe != Object.class);
		return null;
	}

	public static Method[] getMethods(Class classe) {
		List<Method> list = new ArrayList<Method>();
		do {
			for (Method method : classe.getDeclaredMethods()) {
				list.add(method);
			}
			classe = classe.getSuperclass();
		} while (classe != Object.class);
		return list.toArray(new Method[list.size()]);
	}

	/**
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj1
	 * @param id
	 * @return
	 */
	public static Annotation[] getAnnotation(Class classe, Field field) {
		List<Annotation> list = new ArrayList<Annotation>();
		for (Annotation annotation : field.getDeclaredAnnotations()) {
			list.add(annotation);
		}
		Method getter = getGetter(classe, field);
		if(getter != null) {
			for (Annotation annotation : getter.getDeclaredAnnotations()) {
				list.add(annotation);
			}
		}
		
		Method setter = getSetter(classe, field);
		if(setter != null) {
			for (Annotation annotation : setter.getDeclaredAnnotations()) {
				list.add(annotation);
			}
		}
		return list.toArray(new Annotation[list.size()]);
	}

	/**
	 * id > getId, nome -> getNome
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param name
	 * @return
	 */
	public static String getGeterName(String name) {
		if(name.length()>=2) {
			return "get"+name.substring(0,1).toUpperCase() +name.substring(1);
		}else {
			return name;
		}
	}

	/**
	 * id > setId, nome -> setNome
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param name
	 * @return
	 */
	public static String getSeterName(String name) {
		if(name.length()>=2) {
			return "set"+name.substring(0,1).toUpperCase() +name.substring(1);
		}else {
			return name;
		}
	}

	public static Annotation getAnnotation(Class classe, Field field, Class<? extends Annotation> annotationClass) {
		for (Annotation annotation : getAnnotation(classe, field)) {
			if(annotation.getClass() == annotationClass) {
				return annotation;
			}
		}
		return null;
	}

}
