
package org.bqf.common.dto.outside;

import org.bqf.common.dto.BaseReq;
import org.bqf.common.dto.Header;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 类描述: 本类是查询内容详情信息请求
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryContentReqOut extends BaseReq {

    private static final long serialVersionUID = 1L;

    @Builder
    public QueryContentReqOut(Header header, String contentCode, String contentType) {
        super(header);
        this.contentCode = contentCode;
        this.contentType = contentType;
    }

    /**
     * 内容唯一标识
     */
    @NotBlank(message = "内容标识不能为空")
    private String contentCode;

    /**
     * 内容类型, 可选
     */
    private String contentType;
}
