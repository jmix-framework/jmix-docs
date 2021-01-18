package datamodel.ex1.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

// tag::entity[]
@JmixEntity // <1>
public class OperationResult {

    private String result; // <2>

    private Integer errorCode; // <2>

    private String errorMessage; // <2>

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // other getters and setters
    // end::entity[]

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
