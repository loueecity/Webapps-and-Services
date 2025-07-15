package com.webapps2022.jsf;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;

/**
 * create initial accounts (this is used only once)
 */
@Named
@Singleton
public class CreateDefaultAccount {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserService userService;

    // Check for "admin1" default account 
    //  create it if it does not exist
    public void defaultAdmin1() {

        FacesContext context = FacesContext.getCurrentInstance();
        SystemUser user = userService.findAccount("admin1");

        if (user == null) {
            String username = "admin1";
            String userpassword = "admin1";
            String name = "Default";
            String surname = "Admin";
            String currency = "GBP";
            double balance = 1000;

            String usergroup = "admins";

            try {
                SystemUser sys_user;
                SystemUserGroup sys_user_group;

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String passwd = userpassword;
                md.update(passwd.getBytes("UTF-8"));
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < digest.length; i++) {
                    sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                }
                String paswdToStoreInDB = sb.toString();

                sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, balance);
                sys_user_group = new SystemUserGroup(username, usergroup);

                em.persist(sys_user);
                em.persist(sys_user_group);

                context.addMessage(null, new FacesMessage("admin initialised"));
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
                context.addMessage(null, new FacesMessage("admin already initialised"));
              }

    }
}
