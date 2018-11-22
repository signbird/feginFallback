package org.bqf.common.fallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bqf.common.dto.outside.QueryContentRspOut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "fallback")
@Component
@Data
@NoArgsConstructor
public class FallbackConfigHelper {
    
    /**
     * 是否使用fallback配置功能
     */
    private static boolean useFallbackConfig = false;

    private static Map<String/*servicePath*/, List<FallbackConfig>> fallbackConfigMap = new ConcurrentHashMap<>();
    
    /**
     * 增加配置。 
     */
    public static int addConfig(FallbackConfig config){
        Assert.notNull(config, "add FallbackConfig must not be null");
        Assert.notNull(config.getServicePath(), "add FallbackConfig.servicePath must not be null");
        
        String servicePath = config.getServicePath();
        if (fallbackConfigMap.containsKey(servicePath)){
            fallbackConfigMap.get(servicePath).add(config);
        }else{
            List<FallbackConfig> configs = new ArrayList<>();
            configs.add(config);
            fallbackConfigMap.put(servicePath, configs);
        }
        
        return 1;
    }
    
    /**
     * 仅resultValue不同的配置，认为是相同配置. 
     * 相同的配置将id设置到入参中。
     */
    public static boolean containsConfig (FallbackConfig config){
        if (config == null) {
            return false;
        }
        String path = config.getServicePath();
        if (fallbackConfigMap.containsKey(path)){
            List<FallbackConfig> lists = fallbackConfigMap.get(path);
            for (FallbackConfig c : lists){
                if (isSameConfig(c, config)){
                    // 已存在的配置，设置id方便数据库刷新
                    config.setId(c.getId());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 两个配置是否认为是同一个。
     * 标准： 只有resultValue/id 不同，其他都相同的配置，认为相同。
     */
    public static boolean isSameConfig(FallbackConfig a, FallbackConfig b) {
        if (a == null || b == null) {
            return false;
        }

        String serviceNameA = a.getServiceName() == null ? "" : a.getServiceName();
        String serviceNameB = b.getServiceName() == null ? "" : b.getServiceName();
        String servicePathA = a.getServicePath() == null ? "" : a.getServicePath();
        String servicePathB = b.getServicePath() == null ? "" : b.getServicePath();
        
        String paramsA = a.getParams() == null ? "" : a.getParams().toString();
        String paramsB = b.getParams() == null ? "" : b.getParams().toString();
        
        String clientNameA = a.getClientName() == null ? "" : a.getClientName();
        String clientNameB = b.getClientName() == null ? "" : b.getClientName();
        String clientPathA = a.getClientPath() == null ? "" : a.getClientPath();
        String clientPathB = b.getClientPath() == null ? "" : b.getClientPath();
        String resultClassA = a.getResultClass() == null ? "" : a.getResultClass().getName();
        String resultClassB = b.getResultClass() == null ? "" : b.getResultClass().getName();
        
        // 重写 FallbackConfig的equals和hashcode?
        return serviceNameA.equals(serviceNameB) 
                && servicePathA.equals(servicePathB)
                && paramsA.equals(paramsB) 
                && clientNameA.equals(clientNameB)
                && clientPathA.equals(clientPathB) 
                && resultClassA.equals(resultClassB)
                && a.getLevel() == b.getLevel();
    }

    public static int updateConfig(FallbackConfig config) {
        Assert.notNull(config, "update FallbackConfig must not be null");
        Assert.notNull(config.getId(), "update FallbackConfig.id must not be null");
        
        int result = -1;
        for (List<FallbackConfig> lists : fallbackConfigMap.values()){
            for (FallbackConfig f : lists){
                if (config.getId().equals(f.getId())){
                    f = config;
                    result = 1;
                    break;
                }
            }
        }
        
        return result;
    }
    
    public static int deleteById(String configId) {
        Assert.notNull(configId, "id must not be null");
        
        int result = 0;
        for (Map.Entry<String, List<FallbackConfig>> m : fallbackConfigMap.entrySet()){
            List<FallbackConfig> configs = m.getValue();
            for (FallbackConfig config : configs){
                if (configId.equals(config.getId())) {
                    configs.remove(config);
                    result = 1;
                    break;
                }
            }
            
            if (configs.size() == 0) {
                fallbackConfigMap.remove(m.getKey());
                break;
            }
        }
        return result;
    }
    
    public static int deleteByServiceName(String serviceName) {
        Assert.notNull(serviceName, "serviceName must not be null");
        int result = 0;
        
        // 使用迭代器删除，避免ConcurrentModificationException异常
        Iterator<Map.Entry<String, List<FallbackConfig>>> mapIter = fallbackConfigMap.entrySet().iterator();
        while (mapIter.hasNext()) {
            Map.Entry<String, List<FallbackConfig>> entry = mapIter.next();
            List<FallbackConfig> configs = entry.getValue();
            Iterator<FallbackConfig> listIter = configs.iterator();
            while (listIter.hasNext()) {
                FallbackConfig config = listIter.next();
                if (serviceName.equals(config.getServiceName())) {
                    listIter.remove();
                    result++;
                }
            }
            
            if (configs.size() == 0) {
                mapIter.remove();
            }
        }
        
        return result;
    }
    
    public static int deleteByServicePath(String serviceName, String servicePath) {
        Assert.notNull(serviceName, "serviceName must not be null");
        Assert.notNull(serviceName, "servicePath must not be null");
        
        int result = 0;
        if (fallbackConfigMap.containsKey(servicePath)) {
            List<FallbackConfig> configs = fallbackConfigMap.get(servicePath);
            Iterator<FallbackConfig> listIter = configs.iterator();
            while (listIter.hasNext()) {
                FallbackConfig config = listIter.next();
                if (serviceName.equals(config.getServiceName())) {
                    listIter.remove();
                    result++;
                }
            }
            
            if (configs.size() == 0) {
                fallbackConfigMap.remove(servicePath);
            }
        }
        
        return result;
    }
    
    
    public static List<FallbackConfig> getAllFallbackConfig() {
        if (fallbackConfigMap.isEmpty()){
            return null;
        }
        
        List<FallbackConfig> allConfig = new ArrayList<>();
        for (List<FallbackConfig> c : fallbackConfigMap.values()){
            allConfig.addAll(c);
        }
        return allConfig;
    }
    
    public static Map<String, List<FallbackConfig>> getFallbackConfigMap() {
        return fallbackConfigMap;
    }

    public static void setFallbackConfigMap(Map<String, List<FallbackConfig>> fallbackConfigMap) {
        FallbackConfigHelper.fallbackConfigMap = fallbackConfigMap;
    }

    public static boolean isUseFallbackConfig() {
        return useFallbackConfig;
    }

    public static void setUseFallbackConfig(boolean useFallbackConfig) {
        FallbackConfigHelper.useFallbackConfig = useFallbackConfig;
    }
    
    
    // for test
//    static {
//        FallbackConfig f1 = new FallbackConfig();
//        f1.setId("1");
//        f1.setServiceName("service-out");
//        String path1 = "/cartoon/queryContent";
//        f1.setServicePath(path1);
//        Map<String,String> params = new HashMap<>();
//        params.put("contentCode", "3210000000000000000000000100");
//        f1.setParams(params);
//        f1.setResultValue(JSONObject.parseObject(
//                ("{\"contentInfo\": \"default fallback info\",\"result\": {\"resultCode\": \"00000000\",\"resultMessage\": \"controller fallback success\"}}"),
//                QueryContentRspOut.class));
//
//        FallbackConfig f2 = new FallbackConfig();
//        f2.setId("2");
//        f2.setServiceName("service-out");
//        String path2 = "/cartoon/queryContent";
//        f2.setServicePath(path2);
//        f2.setClientServiceName("content-query");
//        f2.setClientPath("/contentCenter/getContent");
//        f2.setResultValue(JSONObject.parseObject(
//                ("{\"contentInfo\": \"default fallback info\",\"result\": {\"resultCode\": \"00000000\",\"resultMessage\": \"client fallback success\"}}"),
//                GetContentRsp.class));
//        
//        FallbackConfig f3 = new FallbackConfig();
//        f3.setId("3");
//        f3.setServiceName("service-out");
//        String path3 = "/cartoon/getPlayDownloadUrl";
//        f3.setServicePath(path3);
//        f3.setClientName("content-query");
//        f3.setClientPath("/contentCenter/getPlayInfo");
//        f3.setResultValue("{fallback success}");
//        
//        fallbackConfigMap = new HashMap<>();
//        addConfig(f1);
//        addConfig(f2);
//        addConfig(f3);
//    }
    
    // TODO ut test
    public static void main(String[] args) {
        System.out.println(fallbackConfigMap);
//        deleteById("3");
//        System.out.println(fallbackConfigMap);
//        deleteById("2");
//        System.out.println(fallbackConfigMap);
//        deleteById("1");
//        System.out.println(fallbackConfigMap);
        
//        System.out.println(deleteByServiceName("service-client"));
//        System.out.println(fallbackConfigMap);
//        System.out.println(deleteByServiceName("service-out"));
//        System.out.println(fallbackConfigMap);
//        System.out.println(deleteByServiceName("service-out"));
//        System.out.println(fallbackConfigMap);
        
//        System.out.println(deleteByServicePath("service-client", "/cartoon/getPlayDownloadUrl"));
//        System.out.println(fallbackConfigMap);
//        System.out.println(deleteByServicePath("service-out", "/cartoon/getPlayDownloadUrl"));
//        System.out.println(fallbackConfigMap);
//        System.out.println(deleteByServicePath("service-out", "/cartoon/queryContent"));
//        System.out.println(fallbackConfigMap);
//        System.out.println(deleteByServicePath("service-out", "/cartoon/queryContent"));
//        System.out.println(fallbackConfigMap);
        
        
        
      FallbackConfig f1 = new FallbackConfig();
      f1.setId("1");
      f1.setServiceName("service-out");
      String path1 = "/cartoon/queryContent";
      f1.setServicePath(path1);
      Map<String,String> params = new HashMap<>();
      params.put("contentCode", "3210000000000000000000000100");
      f1.setParams(params);
      f1.setResultValue(JSONObject.parseObject(
              ("{\"contentInfo\": \"default fallback info\",\"result\": {\"resultCode\": \"00000000\",\"resultMessage\": \"controller fallback success\"}}"),
              QueryContentRspOut.class));
      f1.setResultClass(QueryContentRspOut.class);

      FallbackConfig f2 = new FallbackConfig();
      f2.setId("2");
      f2.setServiceName("service-out");
      String path2 = "/cartoon/queryContent";
      f2.setServicePath(path2);
      Map<String,String> params2 = new HashMap<>();
      params2.put("contentCode", "3210000000000000000000000100");
      f2.setParams(params2);
//      f2.setClientServiceName("content-query");
//      f2.setClientPath("/contentCenter/getContent");
      f2.setResultValue(JSONObject.parseObject(
              ("{\"contentInfo\": \"default fallback info\",\"result\": {\"resultCode\": \"00000000\",\"resultMessage\": \"controller fallback success\"}}"),
              QueryContentRspOut.class));
      f2.setResultClass(QueryContentRspOut.class);
      
      System.out.println(isSameConfig(f1, f2));
    }
}
