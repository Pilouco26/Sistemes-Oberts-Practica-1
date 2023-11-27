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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Comparator;
import model.entities.Comment;
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
    public List<Game> findAlla() {
      return findAll();
    }

    @GET
    @Path("")
    public List<Game> findAllOrderedByName(@QueryParam("type") String type, @QueryParam("console") String console) {
        String queryString;
        Query query;
        if (type == null) {

            queryString = "SELECT g FROM Game g WHERE g.type = :type AND g.console = :console ORDER BY g.name ASC";
            query = em.createQuery(queryString, Game.class);
            query.setParameter(
                    "type", type);

        } else if (console == null) {
            queryString = "SELECT g FROM Game g WHERE g.console = :console ORDER BY g.name ASC";
            query = em.createQuery(queryString, Game.class);
            query.setParameter(
                    "console", console);

        } else {
            queryString = "SELECT g FROM Game g WHERE g.type = :type AND g.console = :console ORDER BY g.name ASC";
            query = em.createQuery(queryString, Game.class);
            query.setParameter(
                    "type", type);
            query.setParameter(
                    "console", console);

        }
        return query.getResultList();
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(Game entity) {
        Game game = new Game();
        game.setName("Example Game");
        game.setType("Action");
        game.setConsole("PlayStation");
        game.setStock(10);
        game.setPathImage("/path/to/image");
        super.create(game);
        
        return Response.status(Response.Status.CREATED).build();
    }

}
