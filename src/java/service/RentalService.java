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
    public Rental findById(@QueryParam("id") Long id) {
        return em.find(Rental.class, id);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crear(@HeaderParam("mailToken") String mailtoken, @HeaderParam("passwordToken") String passwordToken, Game game) {
        
        if(game == null) return Response.status(Response.Status.BAD_REQUEST).build();
        Authentication authentication = new Authentication();
        Rental rental = new Rental();
        rental.setPrice(new Random().nextInt(61) + 20);
        rental.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        rental.setGame(game);
        Customer customer = authentication.check(mailtoken, passwordToken, em);
        if (customer == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        rental.setCustomer(customer);
        Long id = game.getId();
        super.create(rental);
        String message = "Rental uploaded!";

        // Build the JSON response with the custom message
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "success")
                .add("code", Response.Status.CREATED.getStatusCode())
                .add("message", message)
                .add("id", "" + id)
                .add("name", game.getName())
                .add("price", rental.getPrice())
                .build();

        // Return the JSON response with status code 201
        return Response.status(Response.Status.CREATED)
                .entity(jsonResponse.toString())
                .build();

    }

}
