package com.lefonddeletang.controller;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import com.lefonddeletang.view.PodiumService;

/**
 * Initialisation du programme
 */
public class App {
	public static void main(String[] args) throws Exception {

		final String providerClassnames = String.join(";", PodiumService.class.getCanonicalName(), JacksonFeature.class.getCanonicalName());

		final ResourceConfig config = new ResourceConfig()
			    .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
			    .property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true)
			    .property(ServerProperties.PROVIDER_CLASSNAMES, providerClassnames)
			    .property("com.sun.jersey.api.json.POJOMappingFeature", "true");
		final ServletHolder servlet = new ServletHolder(new ServletContainer(config));

		final Server server = new Server(8080);

		final ServletContextHandler context = new ServletContextHandler(server, "/*", ServletContextHandler.GZIP);
		context.addServlet(servlet, "/*");

		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}
}