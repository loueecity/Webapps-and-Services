/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.entity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
/**
 *
 * @author Louis
 */

@NamedQueries({
@NamedQuery(name="getUserTransactions", query="SELECT x FROM TransactionsEntity x WHERE x.senderUsername = :username"),
@NamedQuery(name="getUserRecTransactions", query="SELECT x FROM TransactionsEntity x WHERE x.recipientUsername = :username"),
@NamedQuery(name="getAllTransactions", query="SELECT x FROM TransactionsEntity x")
})

@Entity
public class TransactionsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    @ManyToOne
    SystemUser sender;
    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    @ManyToOne
    SystemUser recipient;

    //@NotNull
    //String currency;

    @NotNull
    double amount;
   
    @NotNull
    String senderUsername;
    @NotNull
    String recipientUsername;
    
    
    public TransactionsEntity(){
    }
    //potentially not working as intended in comparison from the DB it saves
    public TransactionsEntity(SystemUser sender, SystemUser recipient, double amount){
    this.sender = sender;
    this.recipient = recipient;
    this.amount = amount;
    this.senderUsername = sender.getUsername();
    this.recipientUsername = recipient.getUsername();
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemUser getSender() {
        return sender;
    }

    public void setSender(SystemUser sender) {
        this.sender = sender;
    }

    public SystemUser getRecipient() {
        return recipient;
    }

    public void setRecipient(SystemUser recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
