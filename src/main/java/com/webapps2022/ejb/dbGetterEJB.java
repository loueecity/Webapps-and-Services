package com.webapps2022.ejb;

import com.webapps2022.entity.RequestsEntity;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.TransactionsEntity;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//class used to retrieve/show database
@Stateless
public class dbGetterEJB {

   
    @PersistenceContext
    EntityManager em;

    public dbGetterEJB() {
    }

@RolesAllowed("admins")
public List<SystemUser> getAllUsers(){
return em.createNamedQuery("getAllUsers").getResultList();
}

@RolesAllowed("admins")
public List<TransactionsEntity> getAllTranasactions(){
return em.createNamedQuery("getAllTransactions").getResultList();
}

@RolesAllowed("admins")
public List<RequestsEntity> getAllRequests(){
return em.createNamedQuery("getAllRequests").getResultList();
}

@RolesAllowed("users")
public List<RequestsEntity> getRequestsForUser(){
FacesContext context = FacesContext.getCurrentInstance();
SystemUser user = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");
return em.createNamedQuery("getRequestsForUser").setParameter("username", user.getUsername()).getResultList();
}

@RolesAllowed("users")
public List<TransactionsEntity> getTransactionsForUser(){
FacesContext context = FacesContext.getCurrentInstance();
SystemUser user = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");
return em.createNamedQuery("getUserTransactions").setParameter("username", user.getUsername()).getResultList();
}

@RolesAllowed("users")
public List<TransactionsEntity> getRecTransactionsForUser(){
FacesContext context = FacesContext.getCurrentInstance();
SystemUser user = (SystemUser) context.getExternalContext().getSessionMap().get("loggedInUser");
return em.createNamedQuery("getUserRecTransactions").setParameter("username", user.getUsername()).getResultList();
}

}
