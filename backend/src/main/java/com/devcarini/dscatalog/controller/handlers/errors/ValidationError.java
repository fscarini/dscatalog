package com.devcarini.dscatalog.controller.handlers.errors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMesage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addError(String fieldName, String fieldMessage){
        errors.add(new FieldMesage(fieldName, fieldMessage));
    }

    public List<FieldMesage> getErrors() {
        return errors;
    }
}
