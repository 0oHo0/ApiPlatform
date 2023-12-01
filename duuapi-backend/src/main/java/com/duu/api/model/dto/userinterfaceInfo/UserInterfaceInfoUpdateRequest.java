package com.duu.api.model.dto.userinterfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 0-正常 ，1-禁用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}