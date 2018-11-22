package org.bqf.common.dto.inside;

import org.bqf.common.dto.BaseRsp;
import org.bqf.common.dto.Result;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FallbackConfigNotifyRsp extends BaseRsp {
    
    private static final long serialVersionUID = 1L;
    
    @Builder
    public FallbackConfigNotifyRsp(Result result) {
        super(result);
    }

}
