package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectStudentNomClientRequest extends ClientRequest<Object, Produit> {

    public SelectStudentNomClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Produit readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Produit produit = mapper.readValue(body, Produit.class);
        return produit;
    }
}
