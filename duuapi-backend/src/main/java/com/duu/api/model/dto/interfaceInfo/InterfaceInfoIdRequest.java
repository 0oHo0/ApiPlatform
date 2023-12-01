package com.duu.api.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : duu
 * @data : 2023/11/23
 * @from ：https://github.com/0oHo0
 **/
@Data
public class InterfaceInfoIdRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
