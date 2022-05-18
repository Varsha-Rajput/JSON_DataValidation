package com.nagarro.qarangers;


import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonValidator {

    public List<String> validateJson(JsonSchema schema, JsonNode json) {
        assert schema != null;
        assert json != null;

        // create set of validation message and store result in it
        Set<ValidationMessage> validationResult = schema.validate(json);

        // show the validation errors
        if (!validationResult.isEmpty()) {
            // show all the validation error
            return validationResult.stream().map(ValidationMessage::getMessage).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public boolean valueValidationByKey(JsonNode json, String key, String value) {
        assert value != null;
        assert key != null;
        JsonNode node = json.get(key);
        if (node != null) {
            return value.equals(node.asText());
        }
        return false;
    }


    public boolean isKeyExist(final JsonNode jsonNode, final String key) {
        return jsonNode.has(key);
    }
}
