/**
 * 
 */
package com.boeing.aims.vertxdemo;

import com.boeing.aims.vertxdemo.config.IConfigurator;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Create a router which has handlers which handle the Hadoop job GUI. This can be
 * used as a subrouter. It just handles static content.
 * 
 * @author tharter
 *
 */
public class GuiRouterFactory {
	private static volatile Router router;

	public static synchronized Router getRouter(Vertx vertx) {
		if(router == null) {
			router = Router.router(vertx);
			initRouter(vertx);
		}
		return router;
	}
	
	private static void initRouter(Vertx vertx) {
		IConfigurator config = ConfiguratorFactory.getConfiguration();
		String webRoot = (String) config.get(ConfiguratorFactory.GUI_SERVICE_ROOT);

		router.route("/*").handler(StaticHandler.create(webRoot));
		
	}

}
