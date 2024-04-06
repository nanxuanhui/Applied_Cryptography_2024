package com.rsa.project.model.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserStatusRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private long id;
}
