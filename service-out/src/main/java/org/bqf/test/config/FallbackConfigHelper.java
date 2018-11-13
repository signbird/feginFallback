package org.bqf.test.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bqf.test.dto.inside.GetContentRsp;
import org.bqf.test.dto.outside.QueryContentRspOut;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FallbackConfigHelper {
    
    /**
     * 是否使用fallback配置项
     */
    private static boolean useFallbackConfig = true;

    private static Map<String/*path*/, List<FallbackConfig>> fallbackConfigMap;
    
    static {
        
        FallbackConfig f1 = new FallbackConfig();
        f1.setServiceName("service-out");
        String path1 = "/cartoon/queryContent";
        f1.setPath(path1);
        Map<String,String> params = new HashMap<>();
        params.put("contentCode", "3210000000000000000000000100");
        f1.setParams(params);
        f1.setResult(JSONObject.parseObject(
                ("{\"contentInfo\": \"default fallback info\",\"result\": {\"resultCode\": \"00000000\",\"resultMessage\": \"controller fallback success\"}}"),
                QueryContentRspOut.class));

        FallbackConfig f2 = new FallbackConfig();
        f2.setServiceName("service-out");
        String path2 = "/cartoon/queryContent";
        f2.setPath(path2);
        f2.setClientServiceName("content-query");
        f2.setClientPath("/contentCenter/getContent");
        f2.setResult(JSONObject.parseObject(
                ("{\"contentInfo\": \"default fallback info\",\"result\": {\"resultCode\": \"00000000\",\"resultMessage\": \"client fallback success\"}}"),
                GetContentRsp.class));
        
        FallbackConfig f3 = new FallbackConfig();
        f3.setServiceName("service-out");
        String path3 = "/cartoon/getPlayDownloadUrl";
        f3.setPath(path3);
        f3.setClientServiceName("content-query");
        f3.setClientPath("/contentCenter/getPlayInfo");
        f3.setResult("{fallback success}");
        
        fallbackConfigMap = new HashMap<>();
        putConfig(path1, f1);
        putConfig(path2, f2);
        putConfig(path3, f3);
    }
    
    private static void putConfig(String path, FallbackConfig config){
        if (StringUtils.isEmpty(path) || config == null){
            return ;
        }
        if (fallbackConfigMap.containsKey(path)){
            fallbackConfigMap.get(path).add(config);
        }else{
            List<FallbackConfig> configs = new ArrayList<>();
            configs.add(config);
            fallbackConfigMap.put(path, configs);
        }
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
    
}
