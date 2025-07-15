package com.webapps2022.restservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MyRestServiceClass extends Application {

    public MyRestServiceClass() {
    }

    @GET
    @Path("/{currency1}/{currency2}/{amount_of_currency1}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public MOperations getResult(@PathParam("currency1") String cur1, @PathParam("currency2") String cur2, @PathParam("amount_of_currency1") String amount_of_currency1) {
        String currency1 = cur1;
        String currency2 = cur2;
        Double amount;

        try {
            amount = Double.parseDouble(amount_of_currency1);
        } catch (NumberFormatException e) {
            System.out.println("Unable to parse Double from " + e.getMessage());
            return null;
        }

        Double result = null;

        switch (currency1) {
            case "GBP":
                //if converting from GBP
                if ("USD".equals(currency2)){
                result = amount * 1.25;
                }else if("EUR".equals(currency2)){
                result = amount * 1.19;
                }else{
                result = amount;
                } 
                break;
            case "USD":
                //if converting from USD
                if ("GBP".equals(currency2)){
                result = amount * 0.8;
                }else if("EUR".equals(currency2)){
                result = amount * 0.95;
                }else{ result = amount;}
                break;
            case "EUR":
                //if converting from USD
                if ("GBP".equals(currency2)){
                result = amount * 0.84;
                }else if("USD".equals(currency2)){
                result = amount * 1.06;
                }else { result = amount;}
                break;
            default:
                return null;
        }
        MOperations m = new MOperations(currency1,currency2,amount);
        m.setResult(result);
        return m;
    }

}
