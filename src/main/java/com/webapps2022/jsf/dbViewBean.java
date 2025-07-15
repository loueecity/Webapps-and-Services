/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.dbGetterEJB;
import com.webapps2022.entity.RequestsEntity;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.TransactionsEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

//Class used to call the DBGetter EJB to display for the user
@Named("dbViewBean")
@RequestScoped
public class dbViewBean implements Serializable {

@EJB
dbGetterEJB dbGetter;

    
public dbViewBean(){
}

//roles allowed so only admins can see all the users
@RolesAllowed("admins")
public List<SystemUser> getAllUsers(){
return dbGetter.getAllUsers();
}

//roles allowed so only admins can see all the transactions
@RolesAllowed("admins")
public List<TransactionsEntity> getAllTransactions(){
return dbGetter.getAllTranasactions();
}

//only for users
@RolesAllowed("users")
public List<TransactionsEntity> getUserTransactions(){
return dbGetter.getTransactionsForUser();
}

//only for users
@RolesAllowed("users")
public List<TransactionsEntity> getUserRecTransactions(){
return dbGetter.getRecTransactionsForUser();
}

//only for users
@RolesAllowed("users")
public List<RequestsEntity> getUserRequests(){
return dbGetter.getRequestsForUser();
}
}