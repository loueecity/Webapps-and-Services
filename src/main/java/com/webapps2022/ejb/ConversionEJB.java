package com.webapps2022.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ConversionEJB {
    @PersistenceContext
    EntityManager em;

    public ConversionEJB() {
    }

//balance all starts in GBP so needs to be converted for the user and stored to the db
public Double getBalanceConverted(String currency, double amount){

if("GBP".equals(currency)){
amount = amount * 1;
}else if("USD".equals(currency)){
amount = amount * 1.25;
}else if("EUR".equals(currency)){
amount = amount * 1.19;
}
return amount;
}

//Takes parameters currency from, currency to and the amount
public double sendConverted(String currFrom, String currTo, double amount){
Double result=null;
//Switch statement - works through the currency given to give correct amount for the
//required conversion
switch (currFrom) {
            case "GBP":
                //if converting from GBP
                if ("USD".equals(currTo)){
                result = amount * 1.25;
                }else if("EUR".equals(currTo)){
                result = amount * 1.19;
                }else{
                result = amount;
                } 
                break;
            case "USD":
                //if converting from USD
                if ("GBP".equals(currTo)){
                result = amount * 0.8;
                }else if("EUR".equals(currTo)){
                result = amount * 0.95;
                }else{ result = amount;}
                break;
            case "EUR":
                //if converting from USD
                if ("GBP".equals(currTo)){
                result = amount * 0.84;
                }else if("USD".equals(currTo)){
                result = amount * 1.06;
                }else { result = amount;}
                break;
            default:
                return result;
        }

return result;
}
}
