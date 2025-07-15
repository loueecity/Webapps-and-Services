/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.ExecutorEJB;
import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUser;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named("TransactionBean")
@ConversationScoped
public class TransactionBean implements Serializable {
    @EJB
    ExecutorEJB transactionExecutor;
    FacesContext context = FacesContext.getCurrentInstance();
    SystemUser user = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");
    String recipient;
    double amount;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @EJB
    private UserService userService;

    //calls executor EJB to process a payment
    public String makePayment(){
    transactionExecutor.execute(amount,recipient);

    return "users";
    }
}