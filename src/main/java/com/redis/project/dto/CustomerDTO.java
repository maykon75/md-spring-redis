package com.redis.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDTO {
    private String id;
    private String name;
    private String email;
    private String cellPhone;
}
