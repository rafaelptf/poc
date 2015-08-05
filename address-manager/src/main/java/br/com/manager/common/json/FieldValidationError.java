package br.com.manager.common.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class FieldValidationError {

    private String fieldName;
    private String fieldValue;
    private String message;

    public FieldValidationError(String fieldName, String fieldValue, String message) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fieldName", fieldName)
                .append("fieldValue", fieldValue)
                .append("message", message)
                .toString();
    }
}
