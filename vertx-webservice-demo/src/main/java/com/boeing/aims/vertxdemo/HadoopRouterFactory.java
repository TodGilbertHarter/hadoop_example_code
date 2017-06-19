/**
 * 
 */
package com.boeing.aims.vertxdemo;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * Create a router which has handlers which handle the Hadoop job API. This can be
 * used as a subrouter.
 * 
 * @author tharter
 *
 */
public class HadoopRouterFactory {
	private static final Logger LOG = LoggerFactory.getLogger(HadoopRouterFactory.class);
	private static volatile Router router;

	public static synchronized Router getRouter(Vertx vertx) {
		if(router == null) {
			router = Router.router(vertx);
			initRouter();
		}
		return router;
	}
	
	private static void initRouter() {
		String routePath = "/sync/*";
		HadoopLauncher hadoopLauncher = new HadoopLauncher();

		router.route(routePath).blockingHandler(routingContext -> {
			HttpServerRequest req = routingContext.request();
			String message = "";
			int status = 200;
			
			try {
				String command = req.path();

				message = "Launched: "+command+"\n";
				String[] cmdArgs = parseCommand(command);

				LOG.debug("Launching command: '"+command+"'");
				String output = hadoopLauncher.launch(cmdArgs);
				message += " '"+output+"'";
			} catch (Exception e) {
				message = "Failed: "+e.getMessage();
				status = 500;
				LOG.error("Failed to execute",e);
			}
			
			req.response()
				.putHeader("content-type", "text/plain")
				.setStatusCode(status)
				.end(message);
		});
		
	}

	/**
	 * Turn a path component of a URL into a command plus parameters array
	 * suitable for calling HadoopLauncher.launch().
	 * 
	 * @param path path component of URL.
	 * @return String[] command arguments.
	 */
	private static String[] parseCommand(String path) {
		String[] cmdArray = path.split("(\\%20)|(\\+)");
		String[] pathParts = cmdArray[0].split("/");
		cmdArray[0] = pathParts[pathParts.length-1];
		return cmdArray;
	}
}
