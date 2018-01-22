package vertex;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class RestClient {
	private static Logger logger = LoggerFactory.getLogger("vertex.RestClient");

	public static void getReauest(Vertx vertx, String host, String uri) throws Exception{
		WebClient client = WebClient.create(vertx);

		// Send a GET request
		client.get(8080, host, uri).send(ar -> {
		    if (ar.succeeded()) {
		      // Obtain response
		      HttpResponse<Buffer> response = ar.result();
		      logger.debug("Received response with status code" + response.statusCode());
		      
		    } else {
		    	try {
					throw new Exception("Something went wrong " + ar.cause().getMessage());
				} catch (Exception e) {
					logger.error("Something went wrong " + ar.cause().getMessage());
				}
		    }
		  });
	}
}
