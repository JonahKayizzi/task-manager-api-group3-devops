package com.fall2024devops.taskmanager.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse<T> {
    @JsonProperty("message")
    private String message;

    private T payload;

    @JsonProperty("message")
    public String getActualMessage() {
        return message != null ? message.split("/")[0].trim() : null;
    }
}
