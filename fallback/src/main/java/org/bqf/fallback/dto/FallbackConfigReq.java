
package org.bqf.fallback.dto;

import org.bqf.fallback.dao.model.FallbackConfigModel;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FallbackConfigReq {

    @NotBlank
    private FallbackConfigModel fallbackConfig;
}
