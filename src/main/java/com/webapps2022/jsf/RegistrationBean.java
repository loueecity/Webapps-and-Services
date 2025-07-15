package com.webapps2022.jsf;

import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUser;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author parisis
 */
@Named
@RequestScoped
public class RegistrationBean {

    @EJB
    UserService usrSrv;
    
    String username;
    String userpassword;
    String name;
    String surname;
    String currency;

    public RegistrationBean() {

    }

    //call the injected EJB
    public String register() {
        SystemUser user = usrSrv.findAccount(username);
        if (user == null){
        usrSrv.registerUser(username, userpassword, name, surname, currency);
        return "index";
        }else{
         return "index";
         }
    }

    //call the injected EJB
    public String registerAdmin() {
        usrSrv.registerAdmin(username, userpassword, name, surname, currency);
        return "admins";
    }
    
    public UserService getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    
}
