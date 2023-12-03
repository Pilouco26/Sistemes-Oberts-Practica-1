/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authn;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.ws.rs.core.Response;
import model.entities.Customer;

/**
 *
 * @author mlopes
 */
public class Authentication extends RESTRequestFilter {

    public Customer check(String mailtoken, String passwordToken, EntityManager em) {

        String queryString;
        Query query;
        queryString = "SELECT g FROM Customer g WHERE g.email = :email";
        query = em.createQuery(queryString, Customer.class);
        query.setParameter("email", mailtoken);
        Customer customer = (Customer) query.getResultList().get(0);
        if (customer == null) {
            // Customer does not exist
            return null;
        }

        if (!customer.getPassword().equals(passwordToken) || passwordToken == null) {
            // Password is incorrect
            return null;
        }

        return customer;
    }

}
