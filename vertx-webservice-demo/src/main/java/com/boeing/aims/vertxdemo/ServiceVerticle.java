/**
 * 
 */
package com.boeing.aims.vertxdemo;

import com.boeing.aims.vertxdemo.config.IConfigurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Vertx service Verticle which sets up service handler.
 * 
 * @author tharter
 *
 */
public class ServiceVerticle extends AbstractVerticle {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceVerticle.class);
	private final IConfigurator serviceConfigurator = ConfiguratorFactory.getConfiguration();
	
	public void start() {
		LOG.info("Starting Service Verticle");
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		
		String routePath = (String) serviceConfigurator.get(ConfiguratorFactory.HADOOP_SERVICE_URL);
		Router subRouter = HadoopRouterFactory.getRouter(vertx);
		router.mountSubRouter(routePath, subRouter);
		
		routePath = (String) serviceConfigurator.get(ConfiguratorFactory.GUI_SERVICE_URL);
		subRouter = GuiRouterFactory.getRouter(vertx);
		router.mountSubRouter(routePath, subRouter);
		
		server.requestHandler(router::accept).listen(8083);
	}
	
}
