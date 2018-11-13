package org.bqf.content.dto;

import org.hibernate.validator.constraints.Length;
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
public class GetContentReq extends BaseReq{
    
    private static final long serialVersionUID = 1L;

    @Builder
    public GetContentReq(Header requestHeader, String contentCode) {
        super(requestHeader);
        this.contentCode = contentCode;
    }
    
	/**
	 * 内容唯一标识
	 */
	@NotBlank(message = "内容标识不能为空")
	@Length(max = 32,message = "内容标识长度不能超过32位")
	private String contentCode;
}
