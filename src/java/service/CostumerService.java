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
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.persistence.Query;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import model.entities.Comment;
import model.entities.Customer;
import model.entities.Game;

@Stateless
@Path("customer")
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllCustomers() {
        List<Customer> customers = super.findAll();

        // Convert the list of customers to a JSON array
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Customer customer : customers) {
            jsonArrayBuilder.add(
                    Json.createObjectBuilder()
                            .add("id", customer.getId())
                            .add("name", customer.getName())
                            .add("email", customer.getEmail())
                            .build()
            );
        }
        JsonArray jsonArray = jsonArrayBuilder.build();

        // Create a JSON response with the customer data
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("customers", jsonArray)
                .build();

        // Return the JSON response
        return Response.status(Response.Status.OK)
                .entity(jsonResponse.toString())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Long id) {
        Customer customer = em.find(Customer.class, id);

        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "success")
                .add("code", Response.Status.OK.getStatusCode())
                .add("name", customer.getName())
                .add("email", customer.getEmail())
                .build();

        return Response.status(Response.Status.OK)
                .entity(jsonResponse.toString())
                .build();
    }

    @PUT
    @Path("/modifica/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON) // Add this line to specify the response media type
    public Response updateCostumer(@HeaderParam("mailToken") String mailtoken, @HeaderParam("passwordToken") String passwordToken, @PathParam("id") Long id, Customer user) {
        
        Authentication authentication = new Authentication();
        Customer customer = authentication.check(mailtoken, passwordToken, em);
        if (customer == null) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        Customer userToUpdate = em.find(Customer.class, id);
        if (userToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Update customer information
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());

        // Persist the updated customer
        em.persist(userToUpdate);

        // Create a JSON response with the updated customer information
        return Response.ok(userToUpdate).build();
    }

}
