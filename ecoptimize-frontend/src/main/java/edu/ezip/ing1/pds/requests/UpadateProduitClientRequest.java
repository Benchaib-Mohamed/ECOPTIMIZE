package edu.ezip.ing1.pds.requests;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

public class UpadateProduitClientRequest extends ClientRequest {
    public UpadateProduitClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes) throws IOException {
                
                super(networkConfig, myBirthDate, request, info, bytes);
            }

    @Override
    public Produit readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Produit produit = mapper.readValue(body, Produit.class);
        return produit;
    }
}
