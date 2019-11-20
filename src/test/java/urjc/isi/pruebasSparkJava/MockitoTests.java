package urjc.isi.pruebasSparkJava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


import static org.mockito.Mockito.*;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class MockitoTests {

	@Test
	public void MockListSize() {
		List list = mock(List.class); //le pego el cambiazo a la clase list
		when(list.size()).thenReturn(10); //programo el comportamiento que debería tener list cuando llamen a size; devolvemos un 10, porque me viene bn en este caso por ejemlo
		assertEquals(10, list.size()); //mintiendo el mock devuelve 10 y por tanto es verdad sale 10, pasa el test. 
	}

	@Test
	public void MockListSizeWithMultipleReturnValues() {
		List list = mock(List.class);
		when(list.size()).thenReturn(10).thenReturn(20); //primera vez que llamen a size sale 10, la siguiente 20 y las demás sigue con 20, por eso paasan los 3 tests de abajo. 
		assertEquals(10, list.size()); // Primera llamada
		assertEquals(20, list.size()); // Segunda llamada
		assertEquals(20, list.size()); // Tercera llamada y 
		      						  // subsiguientes devuelven el mismo valor
		
		verify(list, times(3)).size(); //comprueba que se ha llamado a list 3 veces a su metodo size, luego pasaría
	}

	@Test
	public void MockListGet() {
		List<String> list = mock(List.class);
		when(list.get(0)).thenReturn("Hello World"); //cuando llamen a list con get y parámetro 0 devuelvo hello world
		assertEquals("Hello World", list.get(0));
		assertNull(list.get(1));
	}

	@Test
	public void MockListGetWithAny() {
		List<String> list = mock(List.class);
		when(list.get(anyInt())).thenReturn("Hello World"); //para cualquier entero menos 3
		when(list.get(3)).thenReturn("Bye World"); //para el entero 3
		
		assertEquals("Hello World", list.get(0));
		assertEquals("Hello World", list.get(1));
		assertEquals("Hello World", list.get(2));
		assertEquals("Bye World", list.get(3));
		
		
	}
	
	@Test
	public void MockIterator_will_return_hello_world(){
		Iterator i = mock(Iterator.class);
		when(i.next()).thenReturn("Hello").thenReturn("World");

		String result=i.next()+" "+i.next();

		assertEquals("Hello World", result);
	}


	@Test
	public void MockWithArguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo("Test")).thenReturn(1);
		assertEquals(1,c.compareTo("Test"));
		assertEquals(0,c.compareTo("Foo"));
	}
	

	@Test
	public void MockWithUnspecifiedArguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo(anyInt())).thenReturn(-1);
		when(c.compareTo(3)).thenReturn(0);
		assertEquals(-1, c.compareTo(5));
		assertEquals(0, c.compareTo(3));
		verify(c).compareTo(5);
		verify(c).compareTo(3);
		verify(c, never()).compareTo(25);
		verify(c, times(1)).compareTo(5);
		verify(c, atLeastOnce()).compareTo(5);
		verify(c, atLeast(1)).compareTo(5);
	}


	@Test(expected=IOException.class)
	public void MockOutputStreamWriterRethrowsAnExceptionFromOutputStream() 
			throws IOException{
		OutputStream mock=mock(OutputStream.class);
		OutputStreamWriter osw=new OutputStreamWriter(mock);
		doThrow(new IOException()).when(mock).close();
		osw.close();
	}
	

	@Test
	public void MockOutputStreamWriterClosesOutputStreamOnClose()
			throws IOException{
		OutputStream mock=mock(OutputStream.class);
		OutputStreamWriter osw=new OutputStreamWriter(mock);
		osw.close();
		verify(mock).close();
	}

}