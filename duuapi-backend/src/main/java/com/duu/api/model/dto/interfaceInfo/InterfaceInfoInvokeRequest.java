package com.duu.api.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : duu
 * @data : 2023/11/23
 * @from ：https://github.com/0oHo0
 **/
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private long id;

    private String requestParam;

    private static final long serialVersionUID = 1L;
}
