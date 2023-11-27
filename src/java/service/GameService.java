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
    @Override
    public List<Game> findAll() {

        return this.findAll();
    }
    @GET
    @Path("?type=${type}&console=${console}")
    public List<Game> findAllOrderedByNameA(@QueryParam("type") String type, @QueryParam("console") String console) {
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
    @Override
    public void create(Game entity) {
        super.create(entity);
    }

}
