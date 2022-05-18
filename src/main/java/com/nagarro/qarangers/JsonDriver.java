package com.nagarro.qarangers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonDriver {

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please provide json schema as first and json file as second argument");
        }
        InputStream jsonSchemaInputStream = fileInputStream(args[0]);
        InputStream jsonInputStream = fileInputStream(args[1]);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909 );

        JsonNode json = null;
        JsonSchema schema = null;
        try {
            json = objectMapper.readTree(jsonInputStream);
            schema = schemaFactory.getSchema(jsonSchemaInputStream);
        } catch (IOException e) {
            System.out.println("Issue in reading json file :"+e.getMessage());
            System.exit(1);
        }

        // get schema from the schemaStream and store it into JsonSchema


        JsonValidator jsonValidator = new JsonValidator();

        //validate schema
        List<String> errorMessages = jsonValidator.validateJson(schema, json);

        for (String msg : errorMessages) {
            System.out.println(msg);
        }

        //validate by key value
        boolean isKeyExist = jsonValidator.isKeyExist(json, "firstname");
        System.out.println("Is key exist {firstname}: "+isKeyExist);

        //validate by key value
        boolean isValidValue = jsonValidator.valueValidationByKey(json, "address.city", "Gurugram");
        System.out.println("Is value exist {firstname}: "+isValidValue);
    }

    private static InputStream fileInputStream(String filePath) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
    }
}
