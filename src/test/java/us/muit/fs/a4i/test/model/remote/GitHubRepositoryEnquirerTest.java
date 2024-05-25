package us.muit.fs.a4i.test.model.remote;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.remote.GitHubRepositoryEnquirer;


/**
 * @author Sergio García López
 *
 */
class GitHubRepositoryEnquirerTest {
	
	private static Logger log = Logger.getLogger(GitHubRepositoryEnquirerTest.class.getName());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
	}

	/**
	 * @throws MetricException
	 */
	@Test
	void testGetTotalPullRequests() throws MetricException {
		
		// Nombre de la métrica que queremos consultar
		String nombreMetrica = "pullResquestTotales";
		
		// Repositorio del que se quiere obtener la métrica
		String repositoryId = "MIT-FS/Audit4Improve-API";
		
		// Variable para almacenar el número total de pull requests 
		ReportItem metrica = null;
		
		// Número total de pull requests que habrá en el repositorio
		int numPullRequests = 61; 
		
        // Creamos el RemoteEnquirer para el repositorio GitHub
        GitHubRepositoryEnquirer enquirer = new GitHubRepositoryEnquirer();

        // Obtenemos el número total de pull requests
        metrica = enquirer.getMetric(nombreMetrica, repositoryId);
        log.info(metrica.toString());

        // Comprobamos que el resultado coincida con el número total de pull requests real
        assertEquals(numPullRequests, metrica.getValue());
		
	}
	
	/**
	 * @throws MetricException
	 */
	@Test
	void testCompletedPullRequests() throws MetricException {
		
		// Nombre de la métrica que queremos consultar
		String nombreMetrica = "pullRequestCompletados";
				
		// Repositorio del que se quiere obtener la métrica
		String repositoryId = "MIT-FS/Audit4Improve-API";
				
		// Variable para almacenar el número de pull requests completados
		ReportItem metrica = null;
				
		// Número de pull requests completados que habrá en el repositorio
		int numCompletedPull = 51; 
				
		// Creamos el RemoteEnquirer para el repositorio GitHub
		GitHubRepositoryEnquirer enquirer = new GitHubRepositoryEnquirer();

		// Obtenemos el número de pull requests completados
		metrica = enquirer.getMetric(nombreMetrica, repositoryId);
		log.info(metrica.toString());

		// Comprobamos que el resultado coincida con el número de pull requests completados real
		assertEquals(numCompletedPull, metrica.getValue());	
	}
		
}
