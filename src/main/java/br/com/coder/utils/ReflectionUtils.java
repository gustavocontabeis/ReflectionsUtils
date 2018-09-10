/**
 * 
 */
package br.com.coder.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

import br.com.coder.utils.dto.MudancaConteudoDTO;
import br.com.coder.utils.entity.Funcionario;
import br.com.coder.utils.exception.ReflectionUtilsException;

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
	 * Retorna o Field mesmo com herança
	 * 
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj
	 * @param nome
	 * @return
	 */
	public static Field getField(Object obj, String nome) {
		Class classe = obj.getClass();
		do {
			Field[] declaredFields = classe.getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.getName().equals(nome)) {
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
				if (!field.getName().equals("serialVersionUID"))
					list.add(field);
			}
			classe = classe.getSuperclass();
		} while (classe != Object.class);
		return list.toArray(new Field[list.size()]);
	}

	public static Field[] getFields(Object obj) {
		return getFields(obj.getClass());
	}

	@Deprecated
	public static Annotation[] getAnnotations(Object obj, String name) {
		List<Annotation> list = new ArrayList<>();
		Field field = getField(obj, name);
		for (Annotation a : field.getDeclaredAnnotations()) {
			list.add(a);
		}
		Method getter = getGetter(obj.getClass(), field);// XXXXXXXXXXXXXXXXXXX
		for (Annotation a : getter.getDeclaredAnnotations()) {
			list.add(a);
		}
		Method setter = getSetter(obj.getClass(), field);// XXXXXXXXXXXXXXXXXXX
		for (Annotation a : setter.getDeclaredAnnotations()) {
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
			if (seterName.equals(method.getName())) {
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
			if (geterName.equals(method.getName())) {
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
		Class classe1 = classe;
		List<Method> list = new ArrayList<Method>();
		do {
			for (Method method : classe1.getDeclaredMethods()) {
				list.add(method);
			}
			classe1 = classe1.getSuperclass();
		} while (classe1 != Object.class);
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
		if (getter != null) {
			for (Annotation annotation : getter.getDeclaredAnnotations()) {
				list.add(annotation);
			}
		}

		Method setter = getSetter(classe, field);
		if (setter != null) {
			for (Annotation annotation : setter.getDeclaredAnnotations()) {
				list.add(annotation);
			}
		}
		return list.toArray(new Annotation[list.size()]);
	}

	/**
	 * id > getId, nome -> getNome
	 * 
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param name
	 * @return
	 */
	public static String getGeterName(String name) {
		if (name.length() >= 2) {
			return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
		} else {
			return name;
		}
	}

	/**
	 * id > setId, nome -> setNome
	 * 
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param name
	 * @return
	 */
	public static String getSeterName(String name) {
		if (name.length() >= 2) {
			return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
		} else {
			return name;
		}
	}

	public static Annotation getAnnotation(Class classe, Field field, Class<? extends Annotation> annotationClass) {
		for (Annotation annotation : getAnnotation(classe, field)) {
			if (annotation.annotationType() == annotationClass) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj
	 * @param atributo
	 * @return
	 * @throws ReflectionUtilsException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValor(Object obj, String atributo) {

		Field field = getField(obj, atributo);
		if (field == null)
			throw new ReflectionUtilsException("A classe " + obj.getClass().getName() + " não possui o atributo " + atributo);

		Method getter = getGetter(obj.getClass(), field);
		if (getter == null)
			return null;

		Object invoke = null;
		try {
			invoke = getter.invoke(obj, new Class[] {});
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return invoke;
	}

	/**
	 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static MudancaConteudoDTO[] buscarDiferencas(Object obj1, Object obj2) {

		List<MudancaConteudoDTO> list = new ArrayList<>();
		Field[] fields = getFields(obj1.getClass());

		for (Field field : fields) {

			Object valor1 = getValor(obj1, field.getName());
			Object valor2 = getValor(obj2, field.getName());

			if (valor1 != null) {
				if (!valor1.equals(valor2)) {
					list.add(new MudancaConteudoDTO(field.getName(), valor1.toString(), valor2 != null ? valor2.toString() : null));
				}
			} else {
				if(valor2 != null) {
					list.add(new MudancaConteudoDTO(field.getName(), (valor1 != null ? valor1.toString() : null), valor2 != null ? valor2.toString() : null));
				}
			}
		}

		return list.toArray(new MudancaConteudoDTO[list.size()]);
	}

}
