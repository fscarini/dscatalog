package com.devcarini.dscatalog.controller.handlers.errors;

public class FieldMesage {

    private String fieldName;
    private String message;

    public FieldMesage() {
    }

    public FieldMesage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
