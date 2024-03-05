package us.muit.fs.a4i.test.model.remote;

/**
 * 
 */


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepositoryStatistics.CodeFrequency;
import org.kohsuke.github.GHProject;


import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHOrganization;

import us.muit.fs.a4i.exceptions.MetricException;
import us.muit.fs.a4i.exceptions.ReportItemException;
import us.muit.fs.a4i.model.entities.Report;
import us.muit.fs.a4i.model.entities.ReportI;
import us.muit.fs.a4i.model.entities.ReportItem;
import us.muit.fs.a4i.model.entities.ReportItemI;
import us.muit.fs.a4i.model.entities.ReportItem.ReportItemBuilder;
import us.muit.fs.a4i.model.remote.GitHubEnquirer;
import us.muit.fs.a4i.model.remote.GitHubOrganizationEnquirer;

/**
 * @author Roberto Lama
 *
 */
public class GitHubOrganizationEnquirerTest {
	private static Logger log = Logger.getLogger(GitHubOrganizationEnquirerTest.class.getName());

	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetPullRequest() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
		// TEST 2: PullRequest
		ReportItem<Integer> metricsPullRequest = ghEnquirer.getMetric("pullRequest", "MIT-FS");
		log.info(metricsPullRequest.getValue().toString());
		assertEquals(22, metricsPullRequest.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 22 pull requests
	}
	
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetRepositories() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
	// TEST 3: Repositories
	ReportItem<Integer> metricsRepositories = ghEnquirer.getMetric("Repositories", "MIT-FS");
	log.info(metricsRepositories.getValue().toString());
	assertEquals(10,metricsRepositories.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 10 repositorios
	}
	
	
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetMembers() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
	// TEST 4: Members
	ReportItem<Integer> metricsMembers = ghEnquirer.getMetric("members", "MIT-FS");
	log.info(metricsMembers.getValue().toString());
	assertEquals(30,metricsMembers.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 30 miembros
	
	}
	

	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetTeams() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
		// TEST 5: Teams
		ReportItem<Integer> metricsTeams = ghEnquirer.getMetric("teams", "MIT-FS");
		log.info(metricsTeams.getValue().toString());
		assertEquals(2,metricsTeams.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 2 teams
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetOpenProjects() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
		// TEST 6: OpenProjects
		ReportItem<Integer> metricsOpenProjects = ghEnquirer.getMetric("openProjects", "MIT-FS");
		log.info(metricsOpenProjects.getValue().toString());
		assertEquals(0,metricsOpenProjects.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 0 proyectos abiertos (classic)
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetClosedProjects() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
		// TEST 7: ClosedProjects
		ReportItem<Integer> metricsClosedProjects = ghEnquirer.getMetric("closedProjects", "MIT-FS");
		log.info(metricsClosedProjects.getValue().toString());
		assertEquals(2,metricsClosedProjects.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 2 proyectos cerrados (classic)
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetRepositoriesWithOpenPullRequest() throws MetricException, ReportItemException {
		GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();
		
		// TEST 1: RepositoriesWithOpenPullRequest
		ReportItem<Integer> metricsRepositoriesWithOpenPullRequest = ghEnquirer.getMetric("repositoriesWithOpenPullRequest", "MIT-FS");
		log.info(metricsRepositoriesWithOpenPullRequest.getValue().toString());
		assertEquals(5,metricsRepositoriesWithOpenPullRequest.getValue(), "Debería tener el valor especificado en el mock"); // Tiene 4 repositorios con pull requests abiertos
	}
	
}
