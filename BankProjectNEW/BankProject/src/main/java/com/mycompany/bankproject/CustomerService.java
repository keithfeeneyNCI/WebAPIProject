/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

/**
 *
 * @author x15015556
 */
import com.google.gson.Gson;
import static java.awt.SystemColor.info;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/customers")
public class CustomerService {

    EntityManager entityManager;

    public CustomerService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

//    @GET
//    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response getPlanets() {
//  
//        List<Planet> list = allEntries();
//
//        return Response.ok(list).build();
//    
//    }
//    
    @GET
    @Path("bank")
    public Response get() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10000);
        System.out.println("\n\n\n\n+go");
        return Response.ok("Some Data").cacheControl(cc).build();

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCustomers() {

        List<BankCustomer> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankCustomer>>(list) {
        };
        return Response.ok(entity).build();

    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllCustomers() {

        List<BankCustomer> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankCustomer>>(list) {
        };
        return Response.ok(entity).build();

    }

    public List<BankCustomer> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankCustomer> cq = cb.createQuery(BankCustomer.class);
        Root<BankCustomer> rootEntry = cq.from(BankCustomer.class);
        CriteriaQuery<BankCustomer> all = cq.select(rootEntry);
        TypedQuery<BankCustomer> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

//    @GET
//    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//    @Path("{id}")
//    public Planet getPlanet(@PathParam("id") int id) {
//        Planet test = entityManager.find(Planet.class, id);
//        return test;
//    }
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{customer_id}")
    public BankCustomer getCustomer(@PathParam("customer_id") int customer_id) {
        BankCustomer test = entityManager.find(BankCustomer.class, customer_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/search")
    public BankCustomer getCustomer(@Context UriInfo info) {

        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));

        BankCustomer test = entityManager.find(BankCustomer.class, customer_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    @Path("/save")
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)

    public Response save(@Context UriInfo info) {

        String first_name = info.getQueryParameters().getFirst("first_name");
        String last_name = info.getQueryParameters().getFirst("last_name");
        String address = info.getQueryParameters().getFirst("address");
        String phone_number = info.getQueryParameters().getFirst("phone_number");
        String email = info.getQueryParameters().getFirst("email");
        String inputted_password = info.getQueryParameters().getFirst("inputted_password");
        boolean verification = false;
        String encrypted_password = "";

        for (int i = inputted_password.length() - 1; i >= 0; i--) {
            encrypted_password = encrypted_password + inputted_password.charAt(i);
        }

        int pin = (int) (Math.random() * 9000) + 1000;
        String status = "ok";

        BankCustomer bc = new BankCustomer();

        bc.setFirst_name(first_name);
        bc.setLast_name(last_name);
        bc.setAddress(address);
        bc.setPhone_number(phone_number);
        bc.setEmail(email);
        bc.setEncrypted_password(encrypted_password);
        bc.setVerification(verification);
        bc.setStatus(status);
        bc.setPin(pin);

        ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
        bc.setAccounts(accounts);
        System.out.println(bc);
        entityManager.getTransaction().begin();

        entityManager.persist(bc);
        entityManager.getTransaction().commit();

        entityManager.close();

        return Response.status(200).entity(bc).build();

    }

    @GET
    @Path("/update")
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    // @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo info) {

        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        String first_name = info.getQueryParameters().getFirst("first_name");
        String last_name = info.getQueryParameters().getFirst("last_name");
        String address = info.getQueryParameters().getFirst("address");
        String phone_number = info.getQueryParameters().getFirst("phone_number");
        String email = info.getQueryParameters().getFirst("email");
        String inputted_password = info.getQueryParameters().getFirst("inputted_password");
        boolean verification = false;
        String encrypted_password = "";
        for (int i = inputted_password.length() - 1; i >= 0; i--) {
            encrypted_password = encrypted_password + inputted_password.charAt(i);
        }

        BankCustomer removedCustomer = entityManager.find(BankCustomer.class, customer_id);
        if (removedCustomer == null) {
            throw new NotFoundException();
        }

        List accounts = removedCustomer.getAccounts();
        int pin = removedCustomer.getPin();
        String status = removedCustomer.getStatus();

        BankCustomer newCustomer = removedCustomer;

        System.out.println("");
        entityManager.getTransaction().begin();

        newCustomer.setFirst_name(first_name);
        newCustomer.setLast_name(last_name);
        newCustomer.setAddress(address);
        newCustomer.setPhone_number(phone_number);
        newCustomer.setEmail(email);
        newCustomer.setEncrypted_password(encrypted_password);
        newCustomer.setVerification(verification);
        newCustomer.setStatus(status);
        newCustomer.setPin(pin);
        newCustomer.setAccounts(accounts);

        entityManager.remove(removedCustomer);
        entityManager.persist(newCustomer);
        entityManager.getTransaction().commit();

        entityManager.close();

        String message = "Record updated";
        return Response.status(200).entity(message).build();

    }

    @GET
    @Path("/delete")
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Context UriInfo info) {

        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        System.out.println("");

        BankCustomer customer = entityManager.find(BankCustomer.class, customer_id);
        if (customer == null) {
            throw new NotFoundException();
        }
        

        entityManager.getTransaction().begin();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();

        entityManager.close();

        String message = "Record deleted";

        return Response.status(200).entity(message).build();
    }
    /*
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(BankCustomer c) {
        System.out.println(c);
        entityManager.getTransaction().begin();

        entityManager.persist(c);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManager.close();

        return Response.status(200).entity(c).build();
    }
     */
}
