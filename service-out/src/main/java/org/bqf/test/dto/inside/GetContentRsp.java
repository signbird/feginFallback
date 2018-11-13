package org.bqf.test.dto.inside;

import org.bqf.test.dto.BaseRsp;
import org.bqf.test.dto.Result;

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
public class GetContentRsp extends BaseRsp{
    
    private static final long serialVersionUID = 1L;
    
    @Builder
    public GetContentRsp(Result result, String content) {
        super(result);
        this.content = content;
    }

    /**
     * 内容信息
     */
	private String content;
}
