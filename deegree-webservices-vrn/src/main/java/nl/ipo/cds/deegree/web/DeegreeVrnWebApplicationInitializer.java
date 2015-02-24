/**
 * 
 */
package nl.ipo.cds.deegree.web;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import nl.idgis.commons.deegree.CrsFilter;
import nl.idgis.commons.deegree.QuirksFilter;
import nl.ipo.cds.deegree.RootConfig;

import org.deegree.services.controller.OGCFrontController;
import org.deegree.services.resources.ResourcesServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author reinoldp
 * 
 */
public class DeegreeVrnWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {

		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootConfig.class);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));

		ServletRegistration.Dynamic ogcFrontController = container.addServlet("services", OGCFrontController.class);
		ogcFrontController.addMapping("/services", "/services/*");
		ogcFrontController.setLoadOnStartup(1);

		FilterRegistration crsFilter = container.addFilter("CrsFilter", CrsFilter.class);
		crsFilter.addMappingForUrlPatterns(null, true, "/services/*");

		FilterRegistration quirksFilter = container.addFilter("QuirksFilter", QuirksFilter.class);
		quirksFilter.addMappingForUrlPatterns(null, true, "/services/*");

		ServletRegistration.Dynamic resourcesServlet = container.addServlet("resources", ResourcesServlet.class);
		resourcesServlet.addMapping("/resources", "/resources/*");
		resourcesServlet.setLoadOnStartup(1);

		container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
				.addMappingForUrlPatterns(null, false, "/*");

		FilterRegistration charEncodingfilterReg = container.addFilter("CharacterEncodingFilter",
				CharacterEncodingFilter.class);
		charEncodingfilterReg.setInitParameter("encoding", "UTF-8");
		charEncodingfilterReg.setInitParameter("forceEncoding", "true");
		charEncodingfilterReg.addMappingForUrlPatterns(null, false, "/*");

	}

}
