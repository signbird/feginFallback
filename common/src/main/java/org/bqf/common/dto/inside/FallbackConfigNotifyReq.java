
package org.bqf.common.dto.inside;

import javax.validation.constraints.NotNull;

import org.bqf.common.dto.BaseReq;
import org.bqf.common.fallback.FallbackConfig;

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
public class FallbackConfigNotifyReq extends BaseReq {

    private static final long serialVersionUID = 1L;
    
    public static final String NOTIFY_TPYE_ADD = "add";
    public static final String NOTIFY_TPYE_UPDATE = "update";
    public static final String NOTIFY_TPYE_DELETE = "delete";

    // TODO @CheckType
    @NotNull
    private String nofityType;
    
    @NotNull
    private FallbackConfig fallbackConfig;
}
