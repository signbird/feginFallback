
package org.bqf.common.dto.inside;

import org.bqf.common.dto.BaseReq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetFallbackConfigReq extends BaseReq {

    private static final long serialVersionUID = 1L;

    /**
     * 请求方的服务名，不填查询所有
     */
    private String serviceName;
}
