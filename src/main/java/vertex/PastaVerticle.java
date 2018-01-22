package vertex;

import java.util.ArrayList;
import java.util.Date;

import infi.test.mock.DataMock;
import infi.test.model.Pasta;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class PastaVerticle extends AbstractVerticle{

	public static int PORT = 8082;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static ArrayList<Pasta> pasataModels = new ArrayList<>();
	
	
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
		
		//router.post("/sauces").handler(this::doPost);
		
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
	
	private void doPost(RoutingContext routingContext) {
		final Pasta model = Json.decodeValue(routingContext.getBodyAsString(), Pasta.class);
		pasataModels.add(model);
		routingContext.response()
	      .setStatusCode(201)
	      .putHeader("content-type", "application/json; charset=utf-8")
	      .end(Json.encodePrettily(pasataModels));
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
