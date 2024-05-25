package us.muit.fs.a4i.test.control;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import us.muit.fs.a4i.control.IssuesRatioIndicatorStrategy;
import us.muit.fs.a4i.control.PullRequestCompletionIndicatorStrategy;
import us.muit.fs.a4i.exceptions.NotAvailableMetricException;
import us.muit.fs.a4i.model.entities.ReportItemI;
import us.muit.fs.a4i.model.entities.IndicatorI.IndicatorState;

class PullRequestCompletionIndicatorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	 @Test
	 public void testCalcIndicator() throws NotAvailableMetricException {
		 // Creamos los mocks 
	     ReportItemI<Double> mockPullReqTotales = Mockito.mock(ReportItemI.class);
	     ReportItemI<Double> mockPullReqCompletados = Mockito.mock(ReportItemI.class);

	     // Configuramos los mocks para que el porcentaje sea el 60%
	     Mockito.when(mockPullReqTotales.getName()).thenReturn("pullRequestTotales");
	     Mockito.when(mockPullReqTotales.getValue()).thenReturn(100.0);

	     Mockito.when(mockPullReqCompletados.getName()).thenReturn("pullRequestCompletados");
	     Mockito.when(mockPullReqCompletados.getValue()).thenReturn(60.0);

	     // Creamos la calculadora del indicador
	     PullRequestCompletionIndicatorStrategy indicadorCalc = new PullRequestCompletionIndicatorStrategy();

	     // Calculamos el indicador
	     List<ReportItemI<Double>> metricas = Arrays.asList(mockPullReqTotales, mockPullReqCompletados);
	     ReportItemI<Double> resultado = indicadorCalc.calcIndicator(metricas);

	     // Comprobamos que el resultado es el esperado
	     Assertions.assertEquals("pullRequestCompletion", resultado.getName());
	     Assertions.assertEquals(60.0, resultado.getValue());
	     Assertions.assertDoesNotThrow(()->indicadorCalc.calcIndicator(metricas));
	  }
	 
	 @Test
	 public void testCalcIndicatorThrowsNotAvailableMetricException() {
	     // Creamos un mock
	     ReportItemI<Double> mockPullReqTotales = Mockito.mock(ReportItemI.class);

	     // Configuramos el mock
	     Mockito.when(mockPullReqTotales.getName()).thenReturn("pullRequestTotales");
	     Mockito.when(mockPullReqTotales.getValue()).thenReturn(100.0);

	     // Creamos la calculadora del indicador
	     PullRequestCompletionIndicatorStrategy indicadorCalc = new PullRequestCompletionIndicatorStrategy();

	     // Probamos con una sola métrica
	     List<ReportItemI<Double>> metricas = Arrays.asList(mockPullReqTotales);
	     // Comprobamos que se lanza la excepción
	     NotAvailableMetricException exception = Assertions.assertThrows(NotAvailableMetricException.class,
	             () -> indicadorCalc.calcIndicator(metricas)); 
	        
	    }
	 
	 @Test
	 public void testRequiredMetrics() {
		 // Creamos la calculadora del indicador
		 PullRequestCompletionIndicatorStrategy indicadorCalc = new PullRequestCompletionIndicatorStrategy();

	     // Obtenemos las metricas del indicador
	     List<String> metricas = indicadorCalc.requiredMetrics();

	     // Comprobamos que el resultado es el esperado
	     List<String> metricasEsperadas = Arrays.asList("pullRequestTotales", "pullRequestCompletados");
	     Assertions.assertEquals(metricasEsperadas, metricas);
	 }

}
