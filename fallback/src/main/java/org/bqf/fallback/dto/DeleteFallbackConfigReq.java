
package org.bqf.fallback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteFallbackConfigReq {

    private String configId;
    
    private String serviceName;
    
    private String servicePath;
    
}
