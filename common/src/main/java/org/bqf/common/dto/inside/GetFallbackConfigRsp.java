package org.bqf.common.dto.inside;

import java.util.List;

import org.bqf.common.dto.BaseRsp;
import org.bqf.common.dto.Result;
import org.bqf.common.fallback.FallbackConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetFallbackConfigRsp extends BaseRsp {
    private static final long serialVersionUID = 1L;

    @Builder
    public GetFallbackConfigRsp(Result result, List<FallbackConfig> configs) {
        super(result);
        this.configs = configs;
    }
    
    private List<FallbackConfig> configs;
}
