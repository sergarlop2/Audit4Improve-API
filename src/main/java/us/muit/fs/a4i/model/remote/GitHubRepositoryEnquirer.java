/**
 * 
 */
package us.muit.fs.a4i.model.remote;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.kohsuke.github.GHRepositoryStatistics.CodeFrequency;
import org.kohsuke.github.GitHub;

import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Report;
import us.muit.fs.a4i.model.entities.ReportI;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItem.ReportItemBuilder;

/**
 * @author Isabel Román
 * Deuda técnica: debería seguir la misma filosofía que GitHubOrganizationEnquirer para evitar la replicación de código
 *
 */
public class GitHubRepositoryEnquirer extends GitHubEnquirer {
	/**
	 * para trazar el código
	 */
	private static Logger log = Logger.getLogger(GitHubRepositoryEnquirer.class.getName());

	/**
	 * <p>
	 * Constructor
	 * </p>
	 */

	public GitHubRepositoryEnquirer() {
		super();
		metricNames.add("subscribers");
		metricNames.add("forks");
		metricNames.add("watchers");
		metricNames.add("starts");		
		metricNames.add("issues");
		metricNames.add("closedIssues");
		metricNames.add("openIssues");
		metricNames.add("creation");
		metricNames.add("lastUpdated");
		metricNames.add("lastPush");
		metricNames.add("totalAdditions");
		metricNames.add("totalDeletions");
		metricNames.add("collaborators");
		metricNames.add("ownerCommits");
		log.info("A�adidas m�tricas al GHRepositoryEnquirer");
	}
    /**
     * Devuelve el informe para el repositorio cuyo id se pasa como parámetro
     */
	@Override
	public ReportI buildReport(String repositoryId) {
		ReportI report = null;
		log.info("Invocado el m�todo que construye un objeto RepositoryReport");
		/**
		 * <p>
		 * Información sobre el repositorio obtenida de GitHub
		 * </p>
		 */
		GHRepository repo;
		/**
		 * <p>
		 * En estos momentos cada vez que se invoca construyeObjeto se crea y rellena
		 * uno nuevo
		 * </p>
		 * <p>
		 * Deuda técnica: se puede optimizar consultando sólo las diferencias respecto a
		 * la fecha de la última representación local
		 * </p>
		 */

		try {
			log.info("Nombre repo = " + repositoryId);

			GitHub gb = getConnection();
			repo = gb.getRepository(repositoryId);
			log.info("El repositorio es de " + repo.getOwnerName() + " Y su descripción es "
					+ repo.getDescription());
			log.info("leído " + repo);
			report = new Report(repositoryId);
			
			/**
			 * Métricas más elaboradas, requieren más "esfuerzo"
			 */
			
			
			report.addMetric(getTotalAdditions(repo));
			log.info("Incluida metrica totalAdditions ");
			
			report.addMetric(getTotalDeletions(repo));
			log.info("Incluida metrica totalDeletions ");


			/**
			 * Métricas directas de tipo conteo
			 */
		
			
			report.addMetric(getSubscribers(repo));
			log.info("Incluida metrica suscribers ");
			
			report.addMetric(getCollaborators(repo));
			log.info("Incluida metrica collaborators ");
			
			report.addMetric(getOwnerCommits(repo));
			log.info("Incluida metrica ownerCommits ");
		
			report.addMetric(getForks(repo));
			log.info("Incluida metrica forks ");
		
			report.addMetric(getWatchers(repo));
			log.info("Incluida metrica watchers ");

			report.addMetric(getStars(repo));
			log.info("Incluida metrica stars ");
			
			report.addMetric(getIssues(repo));
			log.info("Incluida metrica issues ");
			
			report.addMetric(getOpenIssues(repo));
			log.info("Incluida metrica openIssues ");
			
			report.addMetric(getClosedIssues(repo));
			log.info("Incluida metrica closedIssues ");
			
			

		
			/**
			 * Métricas directas de tipo fecha
			 */
			report.addMetric(getCreation(repo));
			log.info("Incluida metrica creation ");
			
			report.addMetric(getLastPush(repo));
			log.info("Incluida metrica lastPush ");
			
			report.addMetric(getLastUpdated(repo));
			log.info("Incluida metrica lastUpdates ");
			
			
		
			
		} catch (Exception e) {
			log.severe("Problemas en la conexión " + e);
		}

		return report;
	}

	/**
	 * Permite consultar desde fuera una única métrica del repositorio indicado
	 * @param metricName el nombre de la métrica
	 * @param repositoryId el id del repositorio
	 * @return el item para incluir en el informe del repositorio
	 */

	@Override
	public ReportItem getMetric(String metricName, String repositoryId) throws MetricException {
		GHRepository remoteRepo;

		GitHub gb = getConnection();
		try {
			remoteRepo = gb.getRepository(repositoryId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetricException(
					"No se puede acceder al repositorio remoto " + repositoryId + " para recuperarlo");
		}

		return getMetric(metricName, remoteRepo);
	}

	/**
	 * <p>
	 * Crea la métrica solicitada consultando el repositorio remoto que se pasa como
	 * parámetro
	 * </p>
	 * 
	 * @param metricName Métrica solicitada
	 * @param remoteRepo Repositorio remoto
	 * @return La métrica creada
	 * @throws MetricException Si la métrica no está definida se lanzará una
	 *                         excepción
	 */
	private ReportItem getMetric(String metricName, GHRepository remoteRepo) throws MetricException {
		ReportItem metric;
		if (remoteRepo == null) {
			throw new MetricException("Intenta obtener una métrica sin haber obtenido los datos del repositorio");
		}
		switch (metricName) {
		case "totalAdditions":
			metric = getTotalAdditions(remoteRepo);
			break;
		case "totalDeletions":
			metric = getTotalDeletions(remoteRepo);
			break;
		case "starts":
			metric = getStars(remoteRepo);
			break;
		case "forks":
			metric = getForks(remoteRepo);
			break;
		case "watchers":
			metric = getWatchers(remoteRepo);
			break;
		case "subscribers":
			metric = getSubscribers(remoteRepo);
			break;
		case "issues":
			metric = getIssues(remoteRepo);
			break;
		case "creation":
			metric = getCreation(remoteRepo);
			break;
		case "lastUpdated":
			metric = getLastUpdated(remoteRepo);
			break;
		case "lastPush":
			metric = getLastPush(remoteRepo);
			break;
		case "collaborators":
			metric = getCollaborators(remoteRepo);
			break;
		case "ownerCommits":
			metric = getOwnerCommits(remoteRepo);
			break;
		case "openIssues":
			metric = getOpenIssues(remoteRepo);
			break;
		case "closedIssues":
			metric = getClosedIssues(remoteRepo);
			break;
		default:
			throw new MetricException("La métrica " + metricName + " no está definida para un repositorio");
		}

		return metric;
	}

	/*
	 * A partir de aquí los algoritmos específicos para hacer las consultas de cada
	 * métrica
	 */

	/**
	 * <p>
	 * Obtención del número total de adiciones al repositorio
	 * </p>
	 * 
	 * @param remoteRepo el repositorio remoto sobre el que consultar
	 * @return la métrica con el número total de adiciones desde el inicio
	 * @throws MetricException Intenta crear una métrica no definida
	 */
	private ReportItem getTotalAdditions(GHRepository remoteRepo) throws MetricException {
		ReportItem metric = null;

		GHRepositoryStatistics data = remoteRepo.getStatistics();
	
		List<CodeFrequency> codeFreq;
		try {
			codeFreq = data.getCodeFrequency();

			int additions = 0;

			for (CodeFrequency freq : codeFreq) {

				if (freq.getAdditions() != 0) {
					Date fecha = new Date((long) freq.getWeekTimestamp() * 1000);
					log.info("Fecha modificaciones " + fecha);
					additions += freq.getAdditions();

				}
			}
			ReportItemBuilder<Integer> builder = new ReportItem.ReportItemBuilder<Integer>("totalAdditions",
					additions);
			builder.source("GitHub, calculada")
					.description("Suma el total de adiciones desde que el repositorio se creó");
			metric = builder.build();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warning("Problemas al leer codefrequency en getTotalAdditions");
			e.printStackTrace();
		} 
		return metric;

	}

	/**
	 * <p>
	 * Obtención del número total de eliminaciones del repositorio
	 * </p>
	 * 
	 * @param remoteRepo el repositorio remoto sobre el que consultar
	 * @return la métrica con el n�mero total de eliminaciones desde el inicio
	 * @throws MetricException Intenta crear una métrica no definida
	 */
	private ReportItem getTotalDeletions(GHRepository remoteRepo) throws MetricException {
		ReportItem metric = null;

		GHRepositoryStatistics data = remoteRepo.getStatistics();
		List<CodeFrequency> codeFreq;
		try {
			codeFreq = data.getCodeFrequency();

			int deletions = 0;

			for (CodeFrequency freq : codeFreq) {

				if (freq.getDeletions() != 0) {
					Date fecha = new Date((long) freq.getWeekTimestamp() * 1000);
					log.info("Fecha modificaciones " + fecha);
					deletions += freq.getAdditions();

				}
			}
			ReportItemBuilder<Integer> totalDeletions = new ReportItem.ReportItemBuilder<Integer>("totalDeletions",
					deletions);
			totalDeletions.source("GitHub, calculada")
					.description("Suma el total de eliminaciones desde que el repositorio se cre�");
			metric = totalDeletions.build();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReportItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metric;

	}
	/**
	 * Devuelve el número de suscriptores
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getSubscribers(GHRepository repo) {
		log.info("Consultando los subscriptores");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("subscribers",
					repo.getSubscribersCount());
			builder.source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve el número de forks
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getForks(GHRepository repo) {
		log.info("Consultando los forks");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("forks",
					repo.getForksCount());
			builder.source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve los usuarios que observan el repositorio
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getWatchers(GHRepository repo) {
		log.info("Consultando los watchers");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("watchers",
					repo.getWatchersCount());
			builder.source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve el número de estrellas
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getStars(GHRepository repo) {
		log.info("Consultando las starts");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("stars",
					repo.getStargazersCount());
			builder.description("Numero de estrellas").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	
	/**
	 * Devuelve el número de commits que realiza el responsable del repositorio
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getOwnerCommits(GHRepository repo) {
		log.info("Consultando los commits del responsable del repositorio");
		ReportItemBuilder<Integer> builder=null;
		GHRepositoryStatistics data = repo.getStatistics();
	
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("ownerCommits",
					data.getParticipation().getOwnerCommits().size());
			
			builder.description("Commits del responsable").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve el número de tickets
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getIssues(GHRepository repo) {
		log.info("Consultando los issues");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("issues",
					repo.getIssues(GHIssueState.ALL).size());
			builder.description("Numero de asuntos totales").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve los issues abiertos en el repositorio que se pasa como parámetro
	 * @param repo repositorio que se consulta
	 * @return item para incluir en el informe
	 */
	private ReportItem getOpenIssues(GHRepository repo) {
		log.info("Consultando los issues abiertos");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("openIssues",
					repo.getOpenIssueCount());
			builder.description("Numero de asuntos abiertos").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve los issues cerrados del repositorio
	 * @param repo repositorio que se consulta
	 * @return item para incluir en el informe
	 */
	private ReportItem getClosedIssues(GHRepository repo) {
		log.info("Consultando los issues cerrados");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("closedIssues",
					repo.getIssues(GHIssueState.CLOSED).size());
			builder.description("Numero de asuntos cerrados").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve el número de colaboradores
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getCollaborators(GHRepository repo) {
		log.info("Consultando los colaboradores");
		ReportItemBuilder<Integer> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Integer>("collaborators",
					repo.getCollaborators().size());
			builder.description("Numero de colaboradores en el repositorio").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}
	/**
	 * Devuelve la fecha de creación del repositorio
	 * @param repo
	 * @return item para el informe
	 */
	private ReportItem getCreation(GHRepository repo) {
		log.info("Consultando fecha de creación");
		ReportItemBuilder<Date> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Date>("creation",
					repo.getCreatedAt());
			builder.description("Fecha de creación del repositorio").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}	
	/**
	 * Devuelve la fecha del último push en el repositorio que se pase como argumento
	 * @param repo repositorio que se consulta
	 * @return item para el informe
	 */
	private ReportItem getLastPush(GHRepository repo) {
		log.info("Consultando el ultimo push");
		ReportItemBuilder<Date> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Date>("lastPush",
					repo.getPushedAt());
			builder.description("Último push realizado en el repositorio").source("GitHub");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}	
	/**
	 * Fecha de la última actualización
	 * @param repo repositorio que se consulta
	 * @return item para incluir en el informe del repositorio
	 */
	private ReportItem getLastUpdated(GHRepository repo) {
		log.info("Consultando la ultima actualización");
		ReportItemBuilder<Date> builder=null;
		try {
			builder = new ReportItem.ReportItemBuilder<Date>("lastUpdated",
					repo.getUpdatedAt());
			builder.description("Última actualización").source("GitHub");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return builder.build();
	}	
	
	
	
}
