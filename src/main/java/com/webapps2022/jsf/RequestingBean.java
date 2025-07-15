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

//Bean used to create transaction request
@Named("RequestingBean")
@ConversationScoped
public class RequestingBean implements Serializable {
    @EJB
    ExecutorEJB requestExecutor;
    FacesContext context = FacesContext.getCurrentInstance();
    SystemUser requestFrom = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");
    String requestTo;
    double amount;

   

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @EJB
    private UserService userService;
 
    //makes the request
    public String makeRequest(){
    requestExecutor.executeRequest(amount,requestTo);

    return "users";
    }

    public SystemUser getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(SystemUser requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(String requestTo) {
        this.requestTo = requestTo;
    }
}