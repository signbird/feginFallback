/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package org.bqf.test.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名称: Rainbow Stone for cartoon
 * 包: com.migu.rstone.dto.outside
 * 类名称: Header.java
 * 类描述: 封装层对外接口消息头
 * 创建人: guhao
 * 创建时间: 2017/8/2 17:59
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "requestHeader")
public class Header implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 协议版本
     */
    @NotBlank(message = "version不能为空")
    private String version;
    /**
     * 交易流水号
     */
    @NotBlank(message = "transactionId不能为空")
    @Length(max = 32, min = 32, message = "长度不等于32位")
    private String transactionId;

    /**
     * 产品线编码
     * TODO 详细的枚举值校验
     */
    @NotBlank(message = "productLine不能为空")
    private String productLine;
    
    /**
     * 接入类型编码
     * TODO 详细的枚举值校验
     */
    @NotBlank(message = "portalType不能为空")
    private String portalType;
    
    /**
     * 参考附录平台标识
     */
    @NotBlank(message = "platform不能为空")
    private String platform;
    
    /**
     * 参考附录厂商标识
     */
    private String companyId;

    /**
     * imei
     */
    private String imei;

    /**
     * imsi
     */
    private String imsi;

    /**
     * 客户端版本号
     */
    private String clientVer;

    /**
     * 应用Id
     */
    private String appid;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 用户访问线索
     */
    private String accessInfo;
    
    /**
     * 灰度使用
     */
    private String extention;
}
