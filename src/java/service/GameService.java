package service;

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
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import model.entities.Comment;
import model.entities.Customer;
import model.entities.Game;

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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/prova")
    public Response findAlla() {
        List<Game> games = findAll();
        return Response.ok(games).build();
    }

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Game findById(@QueryParam("id") Long id) {
        return em.find(Game.class, id);
    }

    @GET
    @Path("/find-all-ordered-by-name")
    public Response findAllOrderedByName(
            @QueryParam("type") @DefaultValue("") String type,
            @QueryParam("console") @DefaultValue("") String console
    ) {
        if (type.equals("") && console.equals("")) {
            // Both parameters are missing or empty
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parameters are not correct.")
                    .build();
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
    public Response crear(Game entity) {

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
