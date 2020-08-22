package com.people;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EducationAppApplicationTests {

	@Test
	void contextLoads() {
		String texto= this.devuelveTextoReves("prueba");
	System.out.print(texto);
	Integer suma=this.devuelveSuma();
	System.out.print(suma);
	
	try {
		  Class.forName("org.postgresql.Driver");
		  //on classpath
		} catch(ClassNotFoundException e) {
		  // not on classpath
			texto="2";
		}
	}


	public String devuelveTextoReves(String texto) {
		char[] lista=texto.toCharArray();
		StringBuilder textoCambiado=new StringBuilder();
		
		for(Integer i=lista.length-1; i>=0; i--) {
			textoCambiado.append(lista[i]);
		}
		return textoCambiado.toString();
	}
	
	public int  devuelveSuma() {
		String texto = "2|5|7|15|25|30";
		Set<Integer> setNumeros= new HashSet<>();
		char[] lista=texto.toCharArray();
		int valorFinal=0;
		String numeroSt="";
		for (char valor: lista) {
			int asciiValue = (int) valor;
			if (asciiValue!=124) {
				numeroSt= numeroSt + valor; 
			}else {
				setNumeros.add(Integer.valueOf(numeroSt));
				numeroSt="";
			}
		}
		setNumeros.add(Integer.valueOf(numeroSt));
		
		for (Integer i: setNumeros) {
			valorFinal= valorFinal + i;
		}
		return valorFinal;
	}
	
	
}
