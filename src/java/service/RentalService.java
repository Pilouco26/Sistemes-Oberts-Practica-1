package service;

import authn.Authentication;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import authn.Secured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.Query;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import model.entities.Customer;
import model.entities.Game;
import model.entities.Rental;
import java.text.SimpleDateFormat;


@Stateless
@Path("rental")
public class RentalService extends AbstractFacade<Rental> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public RentalService() {
        super(Rental.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@QueryParam("id") Long id, @HeaderParam("mailToken") String mailtoken, @HeaderParam("passwordToken") String passwordToken) {
        Authentication authentication = new Authentication();
        Customer customer = authentication.check(mailtoken, passwordToken, em);
        if (customer == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        Rental rental = em.find(Rental.class, id);
        if (rental == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String data = dateFormat.format(rental.getDate());

        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("id", "" + id)
                .add("date", data)
                .add("customer", rental.getCustomer().getName())
                .add("game", rental.getGame().getName())
                .add("price", rental.getPrice())
                .build();

        return Response.status(Response.Status.OK)
                .entity(jsonResponse.toString())
                .build();
    }

    @POST
    @Path("/post")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crear(@HeaderParam("mailToken") String mailtoken, @HeaderParam("passwordToken") String passwordToken, @QueryParam("id") long id) {
        Authentication authentication = new Authentication();
        Customer customer = authentication.check(mailtoken, passwordToken, em);
        if (customer == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Game game = em.find(Game.class, id);
        if (game == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        int stock = game.getStock();
        if (stock == 0) {
            return Response.status(Response.Status.GONE)
                    .entity("Ho sentim, producte ja no està disponible.")
                    .build();
        }
        
        game.setStock(stock-1);
        em.persist(game);
  
        Rental rental = new Rental();
        rental.setPrice(new Random().nextInt(61) + 20);
        LocalDate currentDate = LocalDate.now();
        LocalDate nextMonth = currentDate.plusMonths(1);
        rental.setDate(Date.from(nextMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        rental.setGame(game);
        rental.setCustomer(customer);
        super.create(rental);
        String message = "Rental uploaded!";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String data = dateFormat.format(rental.getDate());
        

        // Build the JSON response with the custom message
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "success")
                .add("code", Response.Status.CREATED.getStatusCode())
                .add("message", message)
                .add("id", "" + rental.getId())
                .add("game", game.getName())
                .add("price", rental.getPrice())
                .add("date", data)
                .build();

        // Return the JSON response with status code 201
        return Response.status(Response.Status.CREATED)
                .entity(jsonResponse.toString())
                .build();

    }

}
