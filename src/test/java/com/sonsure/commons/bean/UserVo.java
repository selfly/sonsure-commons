package com.sonsure.commons.bean;

import com.sonsure.commons.enums.IEnum;
import com.sonsure.commons.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author liyd
 */
@Getter
@Setter
public class UserVo extends Model {

    private static final long serialVersionUID = 3624534909765846265L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private int age;

    private Long amount;

    /**
     * 状态
     */
    private Status status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    private Integer year;

    /**
     * 用户状态，需要和数据字典对应
     */
    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum {

        NORMAL("1", "正常"),

        INACTIVE("2", "未激活"),

        DISABLE_LOGIN("3", "禁止登录");

        private String code;
        private String desc;
    }

}
