/**
 * 
 */
package br.com.coder.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Id;

import org.junit.Test;

import br.com.coder.utils.dto.MudancaConteudoDTO;
import br.com.coder.utils.entity.AnotacaoField;
import br.com.coder.utils.entity.AnotacaoSetter;
import br.com.coder.utils.entity.Funcionario;
import br.com.coder.utils.entity.Pessoa;
import br.com.coder.utils.entity.Sexo;

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
	public void testGetters() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		
		/*
		 * NOTA IMPORTANTE SOBRE REFLECTION<br>
		 * DECLARED:<br>
		 * getFiels X getDeclaredFields<br>
		 * O método getFields, assim como getMethods, funcionam da mesma maneira.<br>
		 * 
		 *  Ex. getFields: retorna somente os atributos acessíveis pelo modificador de acesso [private|deault|protected|public]. Ou seja, não retorma atributos privados se você estiver de fora da classe.<br> 
		 *  Não retorna os atributos herdados e terão que ser buscados manualmente.<br>
		 *  
		 *  getDeclaredFields: Retorna todos atributos. Até mesmo atributos privados.<br>
		 */
		
		assertTrue("getId".equals(ReflectionUtils.getGeterName("id")));
		assertTrue("getNome".equals(ReflectionUtils.getGeterName("nome")));
		assertTrue("getTempoDeDecisao".equals(ReflectionUtils.getGeterName("tempoDeDecisao")));
		
		assertTrue("setId".equals(ReflectionUtils.getSeterName("id")));
		assertTrue("setNome".equals(ReflectionUtils.getSeterName("nome")));
		assertTrue("setTempoDeDecisao".equals(ReflectionUtils.getSeterName("tempoDeDecisao")));
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(null);
		pessoa.setNascimento(new GregorianCalendar(1951, Calendar.FEBRUARY, 22).getTime());
		pessoa.setNome("Lautenir José da Silva");
		pessoa.setSexo(Sexo.MASCULINO);
		
		Funcionario obj1 = new Funcionario();
		obj1.setCtps(123456);
		obj1.setId(null);
		obj1.setNascimento(new GregorianCalendar(1978, Calendar.AUGUST, 26).getTime());
		obj1.setNome("Gustavo da Silva");
		obj1.setSexo(Sexo.MASCULINO);
		obj1.setPai(pessoa);
		
		Field ctps = ReflectionUtils.getField(obj1, "ctps");
		for (Annotation annotation : ctps.getDeclaredAnnotations()) {
			if(annotation instanceof AnotacaoField) {
				AnotacaoField af = (AnotacaoField) annotation;
				System.out.println(af.value());
			}
		}
		
		Field id = ReflectionUtils.getField(obj1, "id");
		assertNotNull(id);
		
		Annotation anotacaoNoAtributo = ReflectionUtils.getAnnotation(obj1.getClass(), id, Id.class);
		assertNotNull(anotacaoNoAtributo);
		assertTrue(anotacaoNoAtributo instanceof Id);
		
		Annotation anotacaoNoGetter = ReflectionUtils.getAnnotation(obj1.getClass(), id, Column.class);
		assertNotNull(anotacaoNoGetter);
		assertTrue(anotacaoNoGetter instanceof Column);
		
		Annotation anotacaoNoSetter = ReflectionUtils.getAnnotation(obj1.getClass(), id, AnotacaoSetter.class);
		assertNotNull(anotacaoNoSetter);
		assertTrue(anotacaoNoSetter instanceof AnotacaoSetter);
		
		assertEquals(ReflectionUtils.getValor(obj1, "ctps"), 123456);
		assertNull(ReflectionUtils.getValor(obj1, "id"));
		assertEquals(ReflectionUtils.getValor(obj1, "nascimento"), new GregorianCalendar(1978, Calendar.AUGUST, 26).getTime());
		assertEquals(ReflectionUtils.getValor(obj1, "nome"), "Gustavo da Silva");
		assertEquals(ReflectionUtils.getValor(obj1, "sexo"), Sexo.MASCULINO);
		assertEquals(ReflectionUtils.getValor(obj1, "pai"), pessoa);
		
		assertEquals(ReflectionUtils.getAnnotation(obj1.getClass(), id).length, 3);
		
		Funcionario obj2 = new Funcionario();
		obj2.setCtps(123456);
		obj2.setId(1L);
		obj2.setNascimento(new GregorianCalendar(1978, Calendar.AUGUST, 27).getTime());
		obj2.setNome("Gustavo da Silva2");
		obj2.setSexo(Sexo.MASCULINO);
		obj2.setPai(pessoa);
		
		MudancaConteudoDTO[] mudancas = ReflectionUtils.buscarDiferencas(obj1, obj2);
		for (MudancaConteudoDTO mudancaConteudoDTO : mudancas) {
			System.out.print("Nome do atributo: "+mudancaConteudoDTO.getNoCampo());
			System.out.print(" - Valor Antes: "+mudancaConteudoDTO.getCoAntes());
			System.out.println(" - Valor Depois: "+ mudancaConteudoDTO.getCoDepois());
		}
		
		
		
	}

}
