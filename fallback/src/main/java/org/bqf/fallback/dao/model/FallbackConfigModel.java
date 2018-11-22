package org.bqf.fallback.dao.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * fallbackConfig持久化对象
 * @author baiqiufei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FallbackConfigModel {

    private String id;
    
    /**
     * 封装层服务名。 ${spring.application.name}
     */
    @NotNull
    private String serviceName;
    
    /**
     * 封装层请求路径
     */
    @NotNull
    private String servicePath;

    /**
     * 请求参数
     * 样例： "contentName=name,contentCode=123456"
     */
    private String params;
    
    /**
     * 原子服务名，或者外部服务的ip:port
     */
    private String clientName;
    
    /**
     * 原子服务请求路径
     */
    private String clientPath;
    
    /**
     * 服务降级返回对象的class全路径
     */
    @NotNull
    private String resultClass;
    
    /**
     * 服务降级返回结果（json格式）
     */
    @NotNull
    private String resultValue;
    
    /**
     * 配置级别。 存在包含关系的多级配置时，级别高的配置生效。
     * 暂未实现。
     */
    private int level;
}
