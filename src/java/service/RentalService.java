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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import model.entities.Customer;
import model.entities.Game;
import model.entities.Rental;
import jakarta.json.JsonArrayBuilder;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String data = dateFormat.format(rental.getDate());
        
        List<Game> games = rental.getGame();
        int i = 0;
        JsonArrayBuilder gamesJson = Json.createArrayBuilder();
        int size = games.size();
        while (i<size) {
            Game game = games.get(i);
            gamesJson.add(game.getName());
            i++;
        }

        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("id", "" + id)
                .add("date", data)
                .add("customer", rental.getCustomer().getName())
                .add("price", rental.getPrice())
                .add("games", gamesJson)
                .build();

        return Response.status(Response.Status.OK)
                .entity(jsonResponse.toString())
                .build();
    }

    @POST
    @Path("/post")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(@HeaderParam("mailToken") String mailtoken, @HeaderParam("passwordToken") String passwordToken, IdListWrapper idListWrapper) {
        
        Authentication authentication = new Authentication();
        Customer customer = authentication.check(mailtoken, passwordToken, em);
        if (customer == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        List<Long> ids = idListWrapper.getIds();
        int i = 0;
        int size = ids.size();
        List<Game> games = new ArrayList();
        if (size == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No games selected")
                    .build();
        }
        else while (i < size) {
            Game game = em.find(Game.class, ids.get(i));
            if (game == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Game " + ids.get(i) + " not found")
                        .build();
            }
            else {
                int stock = game.getStock();
                if (stock == 0) {
                    return Response.status(Response.Status.GONE)
                            .entity("Game " + game.getName() + " is not available")
                            .build();
                }
                else 
                {
                    games.add(game);
                    game.setStock(stock - 1);
                    em.persist(game);
                }
            }
            i++;
        }

        Rental rental = new Rental();
        rental.setPrice(new Random().nextInt(61) + 20);
        LocalDate currentDate = LocalDate.now();
        LocalDate nextMonth = currentDate.plusMonths(1);
        rental.setDate(Date.from(nextMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        rental.setGame(games);
        rental.setCustomer(customer);
        super.create(rental);
        String message = "Rental uploaded!";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String data = dateFormat.format(rental.getDate());
        JsonArrayBuilder gamesJson = Json.createArrayBuilder();
        i = 0;
        size = games.size();
        while (i<size) {
            Game game = games.get(i);
            gamesJson.add(game.getName());
            i++;
        }
        
        // Build the JSON response with the custom message
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "success")
                .add("code", Response.Status.CREATED.getStatusCode())
                .add("message", message)
                .add("id", "" + rental.getId())
                .add("price", rental.getPrice())
                .add("date", data)
                .add("customer", rental.getCustomer().getName())
                .add("games", gamesJson)
                .build();

        // Return the JSON response with status code 201
        return Response.status(Response.Status.CREATED)
                .entity(jsonResponse.toString())
                .build();

    }

}
