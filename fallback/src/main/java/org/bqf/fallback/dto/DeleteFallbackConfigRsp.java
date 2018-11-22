package org.bqf.fallback.dto;

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
public class DeleteFallbackConfigRsp extends BaseRsp {
    private static final long serialVersionUID = 1L;

    @Builder
    public DeleteFallbackConfigRsp(Result result) {
        super(result);
    }
}
