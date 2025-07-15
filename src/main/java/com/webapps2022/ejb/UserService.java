package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author parisis
 */
@Stateless
public class UserService {

   
    @PersistenceContext
    EntityManager em;
    

    @EJB
    ConversionEJB convertEJB;

    public UserService() {
    }

    public void registerUser(String username, String userpassword, String name, String surname, String currency) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Double balanceInGBP = 1000.00; //so each user starts with £1000 that needs to be converted if they have another currency
            Double startingBalance = convertEJB.getBalanceConverted(currency, balanceInGBP);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, startingBalance);
            sys_user_group = new SystemUserGroup(username, "users");

            em.persist(sys_user);
            em.persist(sys_user_group);
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerAdmin(String username, String userpassword, String name, String surname, String currency) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, 1000);
            sys_user_group = new SystemUserGroup(username, "admins");

            em.persist(sys_user);
            em.persist(sys_user_group);
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
   public SystemUser getUser(String username){
   return (SystemUser) em.createNamedQuery("getUser").setParameter("username",username).getResultList().get(0);
   }

 // returns SystemUser Object for the given username
    public SystemUser findAccount(String username) {
        try {
            SystemUser user = (SystemUser) em.createNamedQuery("findaccount").setParameter("username", username).getSingleResult();
            return user;
        } catch (Exception e) {
            System.out.println(username + " : Not Found - " + e);
            return null;
        }
    }

}
