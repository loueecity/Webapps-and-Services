package com.webapps2022.restservice;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class MyRestServiceClient {

    public MOperations runMyRestOperation(String currency1, String currency2, Double amount_of_currency1) {
        System.out.println("Hello: webapps2022 rest service");
        String webappURL = "http://localhost:10000/webapps2022/";
        String restPath = "conversion/" + currency1 + "/" + currency2 + "/" + amount_of_currency1.toString();
        String restapiURI = webappURL + restPath;

        Client client = ClientBuilder.newClient();
        WebTarget myRestResource = client.target(restapiURI);
        Invocation.Builder builder = myRestResource.request(MediaType.APPLICATION_JSON);
        MOperations response = builder.get(MOperations.class);
        client.close();

        return response;
    }
}
