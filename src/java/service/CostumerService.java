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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import model.entities.Comment;
import model.entities.Customer;
import model.entities.Game;

@Stateless
@Path("costumer")
public class CostumerService extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;

    public CostumerService() {
        super(Customer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer findById(@PathParam("id") int id) {
        return em.find(Customer.class, id);
    }

    @PUT
    @Path("/prova/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCostumer(@PathParam("id") Long id, Customer user) {
        Customer userToUpdate = em.find(Customer.class, id);
        if (userToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        em.persist(userToUpdate);
        return Response.ok().build();
    }

}
