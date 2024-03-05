/**
 * 
 */
package us.muit.fs.a4i.model.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTeam;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.GHProject;



import us.muit.fs.a4i.config.MetricConfiguration;
import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Report;
import us.muit.fs.a4i.model.entities.ReportI;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItem.ReportItemBuilder;
import us.muit.fs.a4i.model.entities.ReportItemI;

/**
 * <p>
 * Aspectos generales de todos los informes
 * </p>
 * <p>
 * Todos incluyen un conjunto de métricas de tipo numérico y otro de tipo Date
 * </p>
 * 
 * @author Isabel Román
 *
 */

public class GitHubOrganizationEnquirer extends GitHubEnquirer {
	private static Logger log = Logger.getLogger(Report.class.getName());
	/**
	 * <p>
	 * Identificador unívoco de la entidad a la que se refire el informe en el
	 * servidor remoto que se va a utilizar
	 * </p>
	 */
	private String entityId;
	
	
	public GitHubOrganizationEnquirer() {
		super();
		metricNames.add("members");
		metricNames.add("teams");
		metricNames.add("openProjects");
		metricNames.add("closedProjects");
		metricNames.add("repositoriesWithOpenPullRequest");
		metricNames.add("repositories");
		metricNames.add("pullRequest");
	
		log.info("A�adidas m�tricas al GHRepositoryEnquirer");
	}
	
	@Override
	public ReportI buildReport(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public ReportItem<Integer> getMetric(String metricName, String entityId) throws MetricException {
		log.info("Invocado getMetric para buscar "+metricName);
		// Todas las métricas son de tipo integer (pero esto debería mirarlo en el fichero de configuración mejor...)
		ReportItem<Integer> metric = null;
		List<GHRepository> repos = null;
		ReportItemBuilder<Integer> reportBuilder = null;
		Map<String, GHRepository> repos2 = null;
		List<GHUser> members = null;
		List<GHTeam> teams = null;
		//PagedIterable<GHTeam> teams = null;
		log.info("????");
		PagedIterable<GHProject> projectsIterableOpen = null;
		List<GHProject> projectsOpen = null;
		PagedIterable<GHProject> projectsIterableClosed = null;
		List<GHProject> projectsClosed = null;
		Integer num_members = 0;
		
		
		
		try {
			GitHub gb = getConnection();
			log.info("???? conexion");
			log.info("Identificador de entidad "+entityId);
			GHOrganization remoteOrg = gb.getOrganization(entityId);
			log.info("Obteniendo datos de la organización "+remoteOrg.getName());
			
			MetricConfiguration metricConfiguration = new MetricConfiguration();
			metricConfiguration.listAllMetrics();
			switch (metricName) {
			case "repositoriesWithOpenPullRequest":
				log.info("repositoriesWithOpenPullRequest");
				repos = remoteOrg.getRepositoriesWithOpenPullRequests();
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("repositoriesWithOpenPullRequest",
						repos.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número de repositorios con pull request abiertos.");
				metric = reportBuilder.build();
				break;
			case "repositories":
				log.info("repositories");
				repos2 = remoteOrg.getRepositories();
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("repositories",
						repos2.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número de repositorios de la organización.");
				metric = reportBuilder.build();
				break;
			case "pullRequest":
				log.info("pullRequest");
				List<GHPullRequest> pull_requests = remoteOrg.getPullRequests();
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("pullRequest",
						pull_requests.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de pull requests abiertos de la organización.");
				metric = reportBuilder.build();
				break;
			case "members":
				log.info("members");
				members = remoteOrg.listMembers().toList();
				log.info(String(members.size()));
				
				/*for (Object i: members) {
					num_members++;
					log.info(i);
				}*/
				
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("members", members.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de miembros de la organización.");
				metric = reportBuilder.build();
				break;
			case "teams":
				log.info("teams");
				teams = remoteOrg.listTeams().toList();//.listTeams();//.getTeams();
				log.info(String(teams.size())); //.toList().size());
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("teams",
						teams.size());//teams.toList().size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de equipos de la organización.");
				metric = reportBuilder.build();
				break;
			case "openProjects":
				log.info("openProjects");
				projectsIterableOpen = remoteOrg.listProjects(GHProject.ProjectStateFilter.OPEN);
				projectsOpen = projectsIterableOpen.toList();
				log.info(projectsOpen.toString());
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("openProjects",
						projectsOpen.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de projectos abiertos.");
				metric = reportBuilder.build();
				break;
			case "closedProjects":
				log.info("closedProjects");
				projectsIterableClosed = remoteOrg.listProjects(GHProject.ProjectStateFilter.CLOSED);
				projectsClosed = projectsIterableClosed.toList();
			
				log.info(projectsClosed.toString());
				reportBuilder = new ReportItem.ReportItemBuilder<Integer>("closedProjects",
						projectsClosed.size());
				reportBuilder.source("GitHub")
						.description("Obtiene el número total de projectos cerrados.");
				metric = reportBuilder.build();
				break;
				
			default:
				log.info("NONE");
			}
		}
	    catch (Exception e) {
	    	e.printStackTrace();
	    	throw new MetricException(
				"No se puede acceder a la organización " + entityId + " para consultarlo");
	    }
		
		return metric;
	}
	private String String(int size) {
		// TODO Auto-generated method stub
		return null;
	}
}