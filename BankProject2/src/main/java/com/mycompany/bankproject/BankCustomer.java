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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
@Table
@Produces(MediaType.APPLICATION_JSON)
//@XmlRootElement
public class BankCustomer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customer_id;
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String phone_number;
    private String inputted_password;

     @OneToMany
    @JoinColumn(name = "customer_id")
    private List<BankAccount> accounts;
    

    public BankCustomer() {
       
    }

    public BankCustomer(int customer_id, String first_name, String last_name, String address, String email, String phone_number, String inputted_password, List<BankAccount> accounts) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
        this.inputted_password = inputted_password;
        this.accounts = accounts;
    }

    // private String status;
    // private boolean verification; 
    // private int pin;
//        BankCustomer(String first_name, String last_name, String address, String email, String phone_number, String inputted_password, String status, boolean verification, int pin) {
//            
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
//        EntityManager entitymanager = emfactory.createEntityManager();
//        entitymanager.getTransaction().begin();
//        
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.address = address;
//        this.email = email;
//        this.phone_number = phone_number;
//        this.inputted_password = inputted_password;
//        this.status = status;
//        this.verification = verification;
//        this.pin = pin;
//    }
    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        BankCustomer cust = new BankCustomer();

        entitymanager.persist(cust);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getInputted_password() {
        return inputted_password;
    }

    public void setInputted_password(String inputted_password) {
        this.inputted_password = inputted_password;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "BankCustomer{" + "customer_id=" + customer_id + ", first_name=" + first_name + ", last_name=" + last_name + ", address=" + address + ", email=" + email + ", phone_number=" + phone_number + ", inputted_password=" + inputted_password + ", accounts=" + accounts + '}';
    }

}
