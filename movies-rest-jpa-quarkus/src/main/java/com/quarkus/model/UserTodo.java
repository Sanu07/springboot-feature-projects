package com.quarkus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserTodo {

    private Integer userId;
    @JsonProperty("id")
    private Integer primaryId;
    private String title;
    private boolean completed;
}
