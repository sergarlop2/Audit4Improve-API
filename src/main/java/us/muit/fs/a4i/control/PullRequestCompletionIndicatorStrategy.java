package us.muit.fs.a4i.control;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import us.muit.fs.a4i.exceptions.NotAvailableMetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.IndicatorI.IndicatorState;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItemI;

/**
 * 
 * @author Sergio García López
 *
 */
public class PullRequestCompletionIndicatorStrategy implements IndicatorStrategy<Double> {
	private static Logger log = Logger.getLogger(PullRequestCompletionIndicatorStrategy.class.getName());
	
	// Métricas necesarias para calcular el indicador
	private static final List<String> REQUIRED_METRICS = Arrays.asList("pullRequestTotales", "pullRequestCompletados");

	@Override
	public ReportItemI<Double> calcIndicator(List<ReportItemI<Double>> metricas) throws NotAvailableMetricException {

		// Variables para almacenar las métricas 
	    ReportItemI<Double> totalPullReq = null;
	    ReportItemI<Double> closedPullReq = null;
	    
	    // Indicador a devolver
	    ReportItemI<Double> indicatorReport = null;
	    
	    // Estado del indicador
	    IndicatorState estado = IndicatorState.UNDEFINED;
	    
	    // Buscamos las métricas en la lista
	    for (ReportItemI<Double> metrica : metricas) {
	        if (REQUIRED_METRICS.get(0).equals(metrica.getName())) {
	            totalPullReq = metrica;
	        } else if (REQUIRED_METRICS.get(1).equals(metrica.getName())) {
	            closedPullReq = metrica;
	        }
	    }
	    
	    if (totalPullReq != null && closedPullReq != null) {
	    	
	    	// Calculamos el indicador
	    	Double pullRequestCompletion = 0.0;
	    	
	    	if(closedPullReq.getValue() > 0) {
	    		pullRequestCompletion = closedPullReq.getValue() / totalPullReq.getValue() * 100;
	    	} else {
	    		pullRequestCompletion = closedPullReq.getValue();
	    	}
	    	
	    	// Criterios de calidad (porcentuales)
	    	if (pullRequestCompletion > 75) {
	    		estado = IndicatorState.OK;
	    	} else if (pullRequestCompletion > 50) {
	    		estado = IndicatorState.WARNING;
	    	} else {
	    		estado = IndicatorState.CRITICAL;
	    	}

	    	try {
	    					
	    		indicatorReport = new ReportItem.ReportItemBuilder<Double>("pullRequestCompletion", pullRequestCompletion)
	    							.metrics(Arrays.asList(totalPullReq, closedPullReq))
	    							.indicator(estado).build();
	    		
	    	} catch (ReportItemException e) {
	    		log.info("Error en ReportItemBuilder: " + e);
	    	}
	
	    	
	    } else {
			log.info("Falta alguna de las métricas");
			throw new NotAvailableMetricException(REQUIRED_METRICS.toString());
		}
		
		
		
		
		return indicatorReport;
		
	}

	@Override
	public List<String> requiredMetrics() {
		return REQUIRED_METRICS;
	}
}
