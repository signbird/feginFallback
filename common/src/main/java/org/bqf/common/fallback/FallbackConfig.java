package org.bqf.common.fallback;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FallbackConfig {

//    private static Map<String/*调用方 具体的某个serviceName？*/, FallbackFactory<Object>> fallbackFactoryMap;
    
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
     */
    private Map<String, String> params;

    /**
     * 原子服务名，或者外部服务的ip:port
     */
    private String clientName;
    
    /**
     * 原子服务请求路径
     */
    private String clientPath;
    
    /**
     * 服务降级返回对象的class类型
     */
    private Class<?> resultClass;
    
    /**
     * 服务降级返回结果（json格式）
     * TODO 
     * 1、json格式校验 + 对象类型校验
     * 2、初始化静态对象供使用
     */
    @NotNull
    private Object resultValue;
    
    /**
     * 配置级别。 存在包含关系的多级配置时，级别高的配置生效。
     */
    private int level;
    
}
