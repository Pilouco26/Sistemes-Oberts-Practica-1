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
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import model.entities.Comment;
import model.entities.Customer;
import model.entities.Game;
import model.entities.GameShop;
import model.entities.Shop;

@Stateless
@Path("game")

public class GameService extends AbstractFacade<Game> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public GameService() {
        super(Game.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    @Path("/prova")
    public Response findAlla() {
        List<Game> games = findAllOrderedByName();
        return Response.ok(games).build();
    }
    
    private List<Game> findAllOrderedByName() {
        // Assuming you have a method like findAll() in your data access layer
        // Modify this method accordingly based on your data access mechanism
        List<Game> games = findAll();

        // Use Comparator to sort the list by name
        games.sort(Comparator.comparing(Game::getName));

        return games;
    }
    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findById(@QueryParam("id") Long id) {
        Game game = em.find(Game.class, id);
        if (game == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        // Obtener la lista de relaciones GameShop asociadas al juego
        List<GameShop> gameShops = game.getShops();

        // Crear un array JSON para almacenar las direcciones de las tiendas
        JsonArrayBuilder shopAddressesArray = Json.createArrayBuilder();

    // Iterar sobre la lista de relaciones y agregar las direcciones al array JSON
        for (GameShop gameShop : gameShops) {
        Shop shop = gameShop.getShop();
        if (shop != null) {
        shopAddressesArray.add(shop.getAddress());
        }
}
        
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "success")
                .add("code", Response.Status.OK.getStatusCode())
                .add("name", game.getName())
                .add("type", game.getType())
                .add("console", game.getConsole())
                .add("stock", game.getStock())
                .add("price", game.getPrice())
                .add("description", game.getDescription())
                .add("addresses", shopAddressesArray)
                .add("pathImage", game.getPathImage())
                .build();

        return Response.status(Response.Status.OK)
                .entity(jsonResponse.toString())
                .build();
    }

    @GET
    @Path("/find")
    public Response findAllOrderedByName(
            @QueryParam("type") @DefaultValue("") String type,
            @QueryParam("console") @DefaultValue("") String console
    ) {
        if (type.equals("") && console.equals("")) {
            // Both parameters are missing or empty
            return findAlla();
        }

        String queryString;
        Query query;

        if (type.equals("")) {
            queryString = "SELECT g FROM Game g WHERE g.console = :console ORDER BY g.name ASC";
            query = em.createQuery(queryString, Game.class);
            query.setParameter("console", console);
        } else if (console.equals("")) {
            queryString = "SELECT g FROM Game g WHERE g.type = :type ORDER BY g.name ASC";
            query = em.createQuery(queryString, Game.class);
            query.setParameter("type", type);
        } else {
            queryString = "SELECT g FROM Game g WHERE g.type = :type AND g.console = :console ORDER BY g.name ASC";
            query = em.createQuery(queryString, Game.class);
            query.setParameter("type", type);
            query.setParameter("console", console);
        }

        List<Game> resultList = query.getResultList();
        return Response.ok(resultList).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(@HeaderParam("mailToken") String mailtoken, @HeaderParam("passwordToken") String passwordToken, Game entity) {
        
        Authentication authentication = new Authentication();
        Customer customer = authentication.check(mailtoken, passwordToken, em);
        if (customer == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            // Check if entity name already exists in the database
            String queryString;
            Query query;
            queryString = "SELECT g FROM Game g WHERE g.name = :name";
            query = em.createQuery(queryString, Game.class);
            query.setParameter("name", entity.getName());
            List<Game> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                throw new Exception("Entity with name '" + entity.getName() + "' already exists");
            }

            // Try to create the entity
            super.create(entity);

            // If successful, return a 201 response with a success message
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", "success");
            responseMap.put("code", Response.Status.CREATED.getStatusCode());
            responseMap.put("message", "Entity created successfully");

            return Response.status(Response.Status.CREATED)
                    .entity(responseMap)
                    .build();
        } catch (Exception e) {
            // If an exception occurs (e.g., entity already exists), return a 409 response with an error message
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", "error");
            responseMap.put("code", Response.Status.CONFLICT.getStatusCode());
            responseMap.put("message", e.getMessage());

            return Response.status(Response.Status.CONFLICT)
                    .entity(responseMap)
                    .build();
        }
    }

}
