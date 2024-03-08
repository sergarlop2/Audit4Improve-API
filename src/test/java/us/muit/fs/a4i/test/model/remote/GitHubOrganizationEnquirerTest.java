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
	GitHubOrganizationEnquirer ghEnquirer = new GitHubOrganizationEnquirer();

	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetPullRequest() throws MetricException, ReportItemException {
		
		// TEST 2: PullRequest
		ReportItem<Integer> metricsPullRequest = ghEnquirer.getMetric("pullRequests", "MIT-FS");
		
		assertEquals(21, metricsPullRequest.getValue().intValue(), "el número de pullRequests no es el esperado"); // Tiene 22 pull requests
	}
	
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetRepositories() throws MetricException, ReportItemException {
		
	// TEST 3: Repositories
	ReportItem<Integer> metricsRepositories = ghEnquirer.getMetric("repositories", "MIT-FS");
	
	assertEquals(12,metricsRepositories.getValue().intValue(), "El número de repositorios no es el esperado"); // Tiene 10 repositorios
	}
	
	
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetMembers() throws MetricException, ReportItemException {
		
	// TEST 4: Members
	ReportItem<Integer> metricsMembers = ghEnquirer.getMetric("members", "MIT-FS");

	assertEquals(29,metricsMembers.getValue().intValue(), "El número de miembros no es el esperado"); // Tiene 30 miembros
	
	}
	

	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetTeams() throws MetricException, ReportItemException {
		
		// TEST 5: Teams
		ReportItem<Integer> metricsTeams = ghEnquirer.getMetric("teams", "MIT-FS");
		
		assertEquals(2,metricsTeams.getValue().intValue(), "El número equipos no es el esperado"); // Tiene 2 teams
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetOpenProjects() throws MetricException, ReportItemException {
		
		// TEST 6: OpenProjects
		ReportItem<Integer> op = ghEnquirer.getMetric("openProjects", "MIT-FS");
		assertEquals(0,op.getValue().intValue(),"El número de proyectos abiertos no es el esperado");
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetClosedProjects() throws MetricException, ReportItemException {
		
		// TEST 7: ClosedProjects
		ReportItem<Integer> metricsClosedProjects = ghEnquirer.getMetric("closedProjects", "MIT-FS");
	
		assertEquals(2,metricsClosedProjects.getValue().intValue(), "El número de proyectos cerrados no es el esperado"); 
	}
	
	/**
	 * Test method for
	 * GitHubOrganizationEnquirer
	 * @throws MetricException 
	 * @throws ReportItemException 
	 */
	@Test
	void testGetRepositoriesWithOpenPullRequest() throws MetricException, ReportItemException {
		
		
		// TEST 1: RepositoriesWithOpenPullRequest
		ReportItem<Integer> metricsRepositoriesWithOpenPullRequest = ghEnquirer.getMetric("repositoriesWithOpenPullRequest", "MIT-FS");
	
		assertEquals(6,metricsRepositoriesWithOpenPullRequest.getValue().intValue(), "El número de repositorios con pull requests abiertos no es el esperado"); 
		}
	
}
