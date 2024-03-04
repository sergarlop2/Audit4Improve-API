/**
 * Separo los tests de Context en dos, para evitar el problema con el singleton:
 * Si se modifican los ficheros de configuración los resultados son distintos, porque el orden de ejecución de los tests es aleatorio
 */
package us.muit.fs.a4i.test.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import us.muit.fs.a4i.model.entities.Font; // Clase que sustituye a java.awt.Font

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import us.muit.fs.a4i.config.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import us.muit.fs.a4i.model.entities.IndicatorI;
import us.muit.fs.a4i.model.entities.IndicatorI.IndicatorState;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Isabel Román
 *
 */
class ContextTest2 {
	private static Logger log = Logger.getLogger(CheckerTest.class.getName());
	/**
	 * Ruta al fichero de configuración de indicadores y métricas establecidos por
	 * la aplicación
	 */
	static String appConfPath;
	/**
	 * Ruta al fichero de configuración de propiedades de funcionamiento
	 * establecidos por la aplicación
	 */
	static String appPath;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		appConfPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator
				+ "appConfTest.json";
		appPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "appTest.conf";
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		//Ejecutar tras cada test
	}

	/**
	 * Test method for {@link us.muit.fs.a4i.config.Context#getContext()}.
	 */
	@Test
	void testGetContext() {
		try {
			assertNotNull(Context.getContext(), "Devuelve null");
			assertTrue(Context.getContext() instanceof us.muit.fs.a4i.config.Context, "No es del tipo apropiado");
			assertSame(Context.getContext(), Context.getContext(), "No se devuelve el mismo contexto siempre");

		} catch (IOException e) {
			fail("No debería lanzar esta excepción");
			e.printStackTrace();
		}

	}

	/**
	 * Test method for
	 * {@link us.muit.fs.a4i.config.Context#setAppRI(java.lang.String)}.
	 */
	@Test
	@Tag("Integracion")
	void testSetAppRI() {
		/**
		 * Este test excede los límites, ya que no sólo verifica que se establece bien
		 * la ruta del fichero de especificación de métricas e indicadores sino que se
		 * leen bien los valores del mismo Sería un test de integración porque se
		 * requiere que estén ya desarrollados otras clases, a parte de Context
		 */
		try {
			Context.setAppRI(appConfPath);
			HashMap<String, String> metricInfo = Context.getContext().getChecker().getMetricConfiguration()
					.getMetricInfo("downloads");
			assertNotNull(metricInfo, "No se han leído los atributos de la métrica");
			assertEquals("downloads", metricInfo.get("name"), "El nombre no es el correcto");
			assertEquals("java.lang.Integer", metricInfo.get("type"), "El tipo no es el correcto");
			assertEquals("Descargas realizadas", metricInfo.get("description"), "La descripción no es el correcta");
			assertEquals("downloads", metricInfo.get("unit"), "Las uniddes no son correctas");

		} catch (IOException e) {
			fail("No se encuentra el fichero de especificación de métricas e indicadores");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link us.muit.fs.a4i.config.Context#setAppConf(java.lang.String)}.
	 */
	@Test
	@Tag("Integracion")
	void testSetAppConf() {
		
		try {

			Context.setAppConf(appPath);
			assertTrue(appPath.equals(Context.getAppConf()),"No coincide la ruta del fichero de métricas con la configurada");

		} catch (IOException e) {
			fail("No se encuentra el fichero de especificación de métricas e indicadores");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link us.muit.fs.a4i.config.Context#getChecker()}.
	 */
	@Test
	void testGetChecker() {
		try {
			assertNotNull(Context.getContext().getChecker(), "No devuelve el checker");
			assertTrue(Context.getContext().getChecker() instanceof us.muit.fs.a4i.config.Checker,
					"No es del tipo apropiado");

		} catch (IOException e) {
			fail("No debería devolver esta excepción");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link us.muit.fs.a4i.config.Context#getPersistenceType()}.
	 */
	@Test
	void testGetPersistenceType() {
		try {
			assertEquals("EXCEL", Context.getContext().getPersistenceType(),
					"En el fichero de configuración por defecto, a4i.conf, está definido el tipo excel");
		} catch (IOException e) {
			fail("El fichero no se localiza");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link us.muit.fs.a4i.config.Context#getRemoteType()}.
	 */
	@Test
	void testGetRemoteType() {
		try {
			assertEquals("GITHUB", Context.getContext().getRemoteType(),
					"En el fichero de configuración por defecto, a4i.conf, está el tipo github");
		} catch (IOException e) {
			fail("El fichero no se localiza");
			e.printStackTrace();
		}
	}

	/**
	 * <p>Este test permite verificar que se lee bien la fuente, además es independiente del orden de ejecución del resto de test. La complejidad de la verifiación está impuesta por estar probando un singleton</p>
	 * Test method for {@link us.muit.fs.a4i.config.Context#getDefaultFont()}.
	 */
	@Test
	void testGetDefaultFont() {
		try {
			Font font = null;
			String color; // No entiendo cómo usarlo, ¿para darle valor luego?//
			// Uso esto para ver los tipos de fuentes de los que dispongo
			//String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			//log.info("listado de fuentes " + Arrays.toString(fontNames));
			font = Context.getContext().getDefaultFont();
			assertNotNull(font, "No se ha inicializado bien la fuente");
			//Al ser Context un singleton una vez creada la instancia no se puede eliminar "desde fuera"
			//De manera que el valor de la fuente depende del orden en el que se ejecuten los test, y para que el test sea independiente de eso la verificación comprueba los dos posibles valores
			assertEquals(Color.BLACK.toString(),font.getColor().toString(),"No es el color de fuente especificado en el fichero de propiedades");
			assertEquals(12,font.getFont().getSize(),"No es el tamaño de fuente especificado en el fichero de propiedades");
			assertEquals("Arial",font.getFont().getFamily(),"No es el tipo de fuente especificado en el fichero de propiedades");

		} catch (IOException e) {
			fail("No debería devolver esta excepción");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link us.muit.fs.a4i.config.Context#getMetricFont()}.
	 * @throws IOException 
	 */
	@Test
	
	void testGetMetricFont(){
		try {
		//fail("Not yet implemented");
		Font font = null;
		
		font = Context.getContext().getMetricFont();
		assertNotNull(font, "No se ha inicializado bien la fuente");
		assertEquals(Color.GREEN.toString(),font.getColor().toString(),"No es el color de fuente especificado en el fichero de propiedades");
		assertTrue(15 == font.getFont().getSize(),"No es el tamaño de fuente especificado en el fichero de propiedades");
		assertEquals(java.awt.Font.SERIF,font.getFont().getFamily(),"No es el tipo de fuente especificado en el fichero de propiedades");

		
	}catch (IOException e) {
		fail("No debería devolver esta excepción");
		e.printStackTrace();
		}	
	}

	/**
	 * Test method for
	 * {@link us.muit.fs.a4i.config.Context#getIndicatorFont(us.muit.fs.a4i.model.entities.Indicator.State)}.
	 */
	@Test
	void testGetIndicatorFont() {
		try {
			Font font = null;
			
			// Se le solicita la fuente del estado indefinido, que tendrá los valores por defecto al no estar 
			// definidas sus propiedades en el fichero de configuración utilizados en los tests.
			font = Context.getContext().getIndicatorFont(IndicatorState.UNDEFINED);
			assertNotNull(font, "No se ha inicializado bien la fuente");
			// El nombre o tipo de la fuente podrá ser Arial o Times según el momento en el que se realicen los tests.
			assertEquals("Arial",font.getFont().getFamily(),"No es el tipo de fuente especificado en el fichero de propiedades");
			
			// Se le solicita al contexto la fuente del estao "CRITICAL", cuyas propiedades están definidas en el 
			// fichero de configuración por defecto.
			font = Context.getContext().getIndicatorFont(IndicatorState.CRITICAL);
			assertNotNull(font, "No se ha inicializado bien la fuente");
			assertEquals("Times",font.getFont().getFamily(),"No es el tipo de fuente especificado en el fichero de propiedades");
			assertEquals("RED",font.getColor().toString(),"No es el color de fuente especificado en el fichero de propiedades");
			assertEquals(16,font.getFont().getSize(),"No es el tamaño de fuente especificado en el fichero de propiedades");
			
			} catch (IOException e) {
				fail("No debería devolver esta excepción");
				e.printStackTrace();
			}
	}

	/**
	 * Test method for {@link us.muit.fs.a4i.config.Context#getPropertiesNames()}.
	 */
	@Test
	void testGetPropertiesNames() {
		fail("Not yet implemented");
	}

}
