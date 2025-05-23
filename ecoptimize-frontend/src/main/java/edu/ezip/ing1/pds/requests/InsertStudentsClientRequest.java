package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertStudentsClientRequest extends ClientRequest<Produit, String> {

    public InsertStudentsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Produit info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> studentIdMap = mapper.readValue(body, Map.class);
        final String result  = studentIdMap.get("student_id").toString();
        return result;
    }
}
