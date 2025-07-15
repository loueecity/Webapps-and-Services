/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.entity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
/**
 *
 * @author Louis
 */

@NamedQueries({
@NamedQuery(name="getAllRequests", query="SELECT x FROM RequestsEntity x"),
@NamedQuery(name="getRequestsForUser", query="SELECT x FROM RequestsEntity x WHERE x.requestToUser = :username"),
@NamedQuery(name="getRequest", query="SELECT x FROM RequestsEntity x WHERE x.id = :id")
})

@Entity
public class RequestsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    @ManyToOne
    SystemUser requestFrom;
    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    @ManyToOne
    SystemUser requestTo;

    //@NotNull
    //String currency;

    @NotNull
    double amount;
    
    @NotNull
    String requestFromUser;
    @NotNull
    String requestToUser;
    
    public RequestsEntity(){
    }

    //stores who is requesting and who is being requested from // reskin of the transaction entity
    public RequestsEntity(SystemUser requestFrom, SystemUser requestTo, double amount){
    this.requestFrom = requestFrom;
    this.requestTo = requestTo;
    this.amount = amount;
    this.requestFromUser = requestFrom.getUsername();
    this.requestToUser = requestTo.getUsername();
    }

    public String getRequestFromUser() {
        return requestFromUser;
    }

    public void setRequestFromUser(String requestFromUser) {
        this.requestFromUser = requestFromUser;
    }

    public String getRequestToUser() {
        return requestToUser;
    }

    public void setRequestToUser(String requestToUser) {
        this.requestToUser = requestToUser;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemUser getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(SystemUser requestFrom) {
        this.requestFrom = requestFrom;
    }

    public SystemUser getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(SystemUser requestTo) {
        this.requestTo = requestTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

   
    
}
