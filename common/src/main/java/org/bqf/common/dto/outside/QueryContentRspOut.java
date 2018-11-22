package org.bqf.common.dto.outside;

import org.bqf.common.dto.BaseRsp;
import org.bqf.common.dto.Result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 类描述: 本类是查询内容详情信息响应
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryContentRspOut extends BaseRsp {

    private static final long serialVersionUID = 1L;

    @Builder
    public QueryContentRspOut(Result result, String contentInfo) {
        super(result);
        this.contentInfo = contentInfo;
    }

    private String contentInfo;
}
