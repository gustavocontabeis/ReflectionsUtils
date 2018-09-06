/**
 * 
 */
package br.com.coder.utils;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

import org.junit.Test;

/**
 * @author Gustavo Silva <gustavo.dasilva@castgroup.com.br>
 *
 */
public class ReflectionUtilsTest {

	@Test
	public void testPrintGettersSetters() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIsGetter() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIsSetter() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetters() {
		
		assertTrue("getId".equals(ReflectionUtils.getGeterName("id")));
		assertTrue("getNome".equals(ReflectionUtils.getGeterName("nome")));
		assertTrue("getTempoDeDecisao".equals(ReflectionUtils.getGeterName("tempoDeDecisao")));
		
		assertTrue("setId".equals(ReflectionUtils.getSeterName("id")));
		assertTrue("setNome".equals(ReflectionUtils.getSeterName("nome")));
		assertTrue("setTempoDeDecisao".equals(ReflectionUtils.getSeterName("tempoDeDecisao")));
		
		Funcionario obj1 = new Funcionario();
		obj1.setCtps(123456);
		obj1.setId(null);
		obj1.setNascimento(new Date());
		obj1.setNome("Gustavo da Silva");
		obj1.setSexo(Sexo.MASCULINO);
		
		Field ctps = ReflectionUtils.getField(obj1, "ctps");
		for (Annotation annotation : ctps.getDeclaredAnnotations()) {
			if(annotation instanceof AnotacaoField) {
				AnotacaoField af = (AnotacaoField) annotation;
				System.out.println(af.value());
			}
		}
		
		Field id = ReflectionUtils.getField(obj1, "id");
		assertNotNull(id);
		
		Annotation annField = ReflectionUtils.getAnnotation(obj1.getClass(), id, Id.class);
		Annotation annGetter = ReflectionUtils.getAnnotation(obj1.getClass(), id, Column.class);
		Annotation annSetter = ReflectionUtils.getAnnotation(obj1.getClass(), id, AnotacaoSetter.class);
				
		Annotation[] ann1 = ReflectionUtils.getAnnotation(obj1.getClass(), id);
		Annotation[] ann = ReflectionUtils.getAnnotation(obj1.getClass(), id);
		System.out.println(ann.length);
		
		/**
		 * Declared [Fields|Methos|Annotations] retorna todos os atributos|métodos|anotações mesmo sendo privados.
		 */
		System.out.println("--- Declared fields: ");
		for (Field field : obj1.getClass().getDeclaredFields()) {
			System.out.println("      "+field.getName());
		}
		
		/**
		 * Fields [Fields|Methos|Annotations] retorna todos os atributos|métodos|anotações públicos. Para privados use o Declared.
		 */
		System.out.println("--- Fields: ");
		for (Field field : obj1.getClass().getFields()) {
			System.out.println("      "+field.getName());
		}
		
	}

}
