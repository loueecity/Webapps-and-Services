package com.webapps2022.ejb;

import com.webapps2022.entity.RequestsEntity;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.TransactionsEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import com.gp225.transactions.operations.Operation;

@Stateless
@TransactionAttribute(REQUIRED)
public class ExecutorEJB {

@PersistenceContext
EntityManager em;

@EJB
private UserService usrSrv;
@EJB
private ConversionEJB convertEJB;

//Executes a transaction - takes amount and the username of the recipient
public String execute(double amount, String recipient){
//get sender sysuser account
FacesContext context = FacesContext.getCurrentInstance();
SystemUser senderUser = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");

//checks if the recipient user exists
SystemUser recipientUserCheck = usrSrv.findAccount(recipient);
        if (recipientUserCheck == null){
context.addMessage(null, new FacesMessage("Error finding recipient. Funds have not been sent"));
return "Please ensure you are typing the username of the recipient right or that it exists.";
}

//if user exists stores the sysuser in variable recipientUser
SystemUser recipientUser = usrSrv.getUser(recipient);

//further validation checks ensuring the user is sending to another user
//checks the user has the required balance to send and that the value is greater than 0
if(senderUser.getUsername() == recipientUser.getUsername() || senderUser.getAccountBalance() < amount || amount < 0){
context.addMessage(null, new FacesMessage("Please ensure you are sending to another user and that you have sufficient funds"));
return "Please ensure you are sending to another user and that you have sufficient funds";
}
//currency conversion
String currFrom = senderUser.getCurrency(); //gets sending user currency
String currTo = recipientUser.getCurrency(); //gets receiving user currency
Double sendAmount = convertEJB.sendConverted(currFrom, currTo, amount); //converts amount sent

try {

//for entity
SystemUser sender = em.find(SystemUser.class, senderUser.getId());
SystemUser rec = em.find(SystemUser.class, recipientUser.getId());

//transaction minuses the amount sent from the sending account 
//and adds the amount to the current balance of the receipient
//need to add checks for their account balance before to ensure users have the value of the amount being sent
//subtracts what the user sent in their own currency and sends the converted currency to the recipient
sender.setAccountBalance(sender.getAccountBalance()-amount);
rec.setAccountBalance(rec.getAccountBalance()+sendAmount);
TransactionsEntity transactions = new TransactionsEntity(senderUser,recipientUser,sendAmount);

//updates entity database
em.persist(sender);
em.persist(rec);
em.persist(transactions);
context.addMessage(null, new FacesMessage("Payment processed"));
return "Payment Processed";
}catch(EJBTransactionRolledbackException e){
return "Error completing transaction";
}

}

//creates request 
public String executeRequest(double amount, String requestFrom){
//get sender sysuser account
FacesContext context = FacesContext.getCurrentInstance();
SystemUser requestFromUser = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");

//checks requested user exists
SystemUser recipientUserCheck = usrSrv.findAccount(requestFrom);
        if (recipientUserCheck == null){
context.addMessage(null, new FacesMessage("Error finding accont to request. Please ensure you are typing the username correctly"));
return "Please ensure you are typing the username of the recipient right or that it exists.";
}

//stores requested user
SystemUser requestToUser = usrSrv.getUser(requestFrom);
if (requestFromUser.getUsername()== requestToUser.getUsername()){
context.addMessage(null, new FacesMessage("You cannot request funds from yourself!"));
return "You cannot request yourself!";
}
try {
//similar to the execution - 
SystemUser requestFromSys = em.find(SystemUser.class, requestFromUser.getId());
SystemUser requestToSys = em.find(SystemUser.class, requestToUser.getId());
//transaction minuses the amount sent from the sending account 
//and adds the amount to the current balance of the receipient
//need to add checks for their account balance before to ensure users have the value of the amount being sent
//
//creates the request and stores it in the request database
RequestsEntity request = new RequestsEntity(requestFromSys, requestToSys,amount);
em.persist(request);
context.addMessage(null, new FacesMessage("Request made"));
return "Request made";
}catch(EJBTransactionRolledbackException e){
return "Error completing request";
}

}

//Processes the request
public String executeProcessRequest(Long id){
RequestsEntity request = em.find(RequestsEntity.class, id); //retrieves the request of the ID
Double amount = request.getAmount(); // retrieves the amount

//gets user sysuser
FacesContext context = FacesContext.getCurrentInstance();
SystemUser senderUser = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");

//gets recipientuser
String recipientUser = request.getRequestFromUser();
SystemUser recipient = usrSrv.getUser(recipientUser);

//converts the users currency if it is different from the user requesting currency
String currFrom = senderUser.getCurrency();
String currTo = recipient.getCurrency();
Double amountTaken = convertEJB.sendConverted(currTo, currFrom, amount);

try {

SystemUser sender = em.find(SystemUser.class, senderUser.getId());
SystemUser rec = em.find(SystemUser.class, recipient.getId());

//transaction minuses the amount sent from the sending account 
//and adds the amount to the current balance of the receipient
//need to add checks for their account balance before to ensure users have the value of the amount being sent
//
sender.setAccountBalance(sender.getAccountBalance()-amountTaken);
rec.setAccountBalance(rec.getAccountBalance()+amount);
TransactionsEntity transactions = new TransactionsEntity(senderUser,recipient,amount);

//performs the transaction
em.persist(sender);
em.persist(rec);
em.persist(transactions);

//removes the request from the request entity
em.remove(request);
context.addMessage(null, new FacesMessage("You have sent the requested funds"));
return "Payment Processed";
}catch(EJBTransactionRolledbackException e){
return "Error completing transaction";
}
}

//Declines the request
public String executeDeclineRequest(Long id){
RequestsEntity request = em.find(RequestsEntity.class, id);
FacesContext context = FacesContext.getCurrentInstance();
try {
//removes the request from the database
em.remove(request);
context.addMessage(null, new FacesMessage("You have declined the request"));
return "Request Declined";
}catch(EJBTransactionRolledbackException e){
return "Error completing transaction";
}

}

}
