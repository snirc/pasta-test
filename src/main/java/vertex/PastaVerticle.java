package vertex;

import java.util.ArrayList;
import java.util.Date;

import infi.test.mock.DataMock;
import infi.test.model.Pasta;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class PastaVerticle extends AbstractVerticle{

	public static int PORT = 8082;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static ArrayList<Pasta> pasataModels = new ArrayList<>();
	private static String FOOD2FORK_URL = "food2Fork.com/api/search";
	private static String FOOD2FORK_SEARCH_URI = "/api/search";
	private static String FOOD2FORK_RECIPE_URI = "/api/search";
	private static String FOOD2FORK_KEY = "510b4b833870c160aeb1b8dbb6c10178";
	
	@Override
    public void start(Future<Void> startFuture) {
		
        logger.info("Vertix Started on port " + PORT);
        
		// Create a router object.
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create().setMergeFormAttributes(true));
		
		HttpServer server = vertx.createHttpServer();
		
	    server.requestHandler(router::accept).listen(PORT);
	    	    
	 // Allow Cross-Origin for post/get methods
	 		router.route().handler(CorsHandler.create("*")
	 				.allowedMethod(HttpMethod.GET)
	 				.allowedMethod(HttpMethod.POST)
	 				.allowedHeader("Content-Type"));
	    
		// Bind "/" to our default message.
		router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html")
					.end("<h1>Hellow Pasta is running ...</h1><BR> port: " + PORT +" "+ new Date());
		});
		
		router.get("/pastas").handler(this::getAllPasta);
		
		router.get("/pastas/:type").handler(this::getPasta);

		router.get("/sauces").handler(this::getAllSauces);
		
		router.get("/pasta-recipe").handler(this::getPastaRecipe);
		
    }

	
	private void getAllPasta(RoutingContext routingContext) {
		try {
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(DataMock.getPastaList().values()));
			

		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.end("Error loading extensions.json: "+e.getMessage());
		}
	}

	
	private void getPasta(RoutingContext routingContext) {
		try {
			String type = routingContext.request().getParam("type");
			logger.debug(type);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(DataMock.getPastaList().get(type)));
			

		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.end("Error loading extensions.json: "+e.getMessage());
		}
	}
	
	
	private void getAllSauces(RoutingContext routingContext) {
		try {
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(DataMock.getSauceList().values()));
			

		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.end("Error loading extensions.json: "+e.getMessage());
		}
	}
	

	private void getPastaRecipe(RoutingContext routingContext) {
		try {
			
			WebClient client = WebClient.create(vertx);

			// Send a GET request
			client.get( FOOD2FORK_URL, FOOD2FORK_SEARCH_URI+"?key="+FOOD2FORK_KEY+"&q=Spaghetti")				
				.timeout(5000)
				.send(ar -> {
					if (ar.succeeded()) {
				      // Obtain response
				      HttpResponse<Buffer> response = ar.result();
				      logger.info("Received response with status code" + response.statusCode());
				      
				      routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.end(response.bodyAsString());
				    } else {
				    	logger.error("ERROR! " + ar.cause().getMessage());
				    }
				});
			
			
			

		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.end("Error loading extensions.json: "+e.getMessage());
		}
	}

	
    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("MyVerticle stopped!");
    }
    
	public static void main(String[] args) {
		DataMock.init();
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new PastaVerticle());
	}

}
