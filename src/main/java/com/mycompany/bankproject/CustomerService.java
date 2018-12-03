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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    
    public CustomerService(){
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
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCustomers() {
  
        List<BankCustomer> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankCustomer>>(list){};
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
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{customer_id}")
    public BankCustomer getCustomer(@PathParam("customer_id") int customer_id) {
        BankCustomer test = entityManager.find(BankCustomer.class, customer_id);
        if (test == null){
            throw new NotFoundException("You dun goofed");
        }
        return test;
    }
     
    
//    @GET
//    @Path("/save")
//    @Produces("application/json")
//    public Response save(@Context UriInfo info){
//        Gson gson = new Gson();
//        
//        String first_name = info.getQueryParameters().getFirst("first_name");
//        String last_name = info.getQueryParameters().getFirst("last_name");
//        String address = info.getQueryParameters().getFirst("address");
//        String phone_number = info.getQueryParameters().getFirst("phone_number");
//        String email = info.getQueryParameters().getFirst("email");
//        String inputted_password = info.getQueryParameters().getFirst("inputted_password");
//        String encrypted_password = info.getQueryParameters().getFirst("encrypted_password");
//        boolean verification = Boolean.parseBoolean(info.getQueryParameters().getFirst("verification"));
//        int pin = Integer.parseInt(info.getQueryParameters().getFirst("pin"));
//        String status = "ok";
//        
//        
//
//        BankCustomer bc = new BankCustomer();
//        System.out.println("");
//
//       return Response.status(200).entity(gson.toJson(bc)).build();
//       
//    }
    
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
}
