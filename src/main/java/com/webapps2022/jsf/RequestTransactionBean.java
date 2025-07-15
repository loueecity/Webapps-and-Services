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

//used for requesting
@Named("requestTransactionBean")
@ConversationScoped
public class RequestTransactionBean implements Serializable {
    @EJB
    ExecutorEJB transactionExecutor;
    FacesContext context = FacesContext.getCurrentInstance();
    SystemUser user = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");
    Long requestId;
    //String recipient;
    //double amount;

    public ExecutorEJB getTransactionExecutor() {
        return transactionExecutor;
    }

    public void setTransactionExecutor(ExecutorEJB transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public RequestTransactionBean(){
    }

    //calls transaction EJB to process the request
    public String makeRequestPayment(Long requestId){
    transactionExecutor.executeProcessRequest(requestId);
    return "users";
    }
    
    //calls transaciton EJB to decline the request
    public String declineRequestPayment(Long requestId){
    transactionExecutor.executeDeclineRequest(requestId);
    return "users";
    }
}