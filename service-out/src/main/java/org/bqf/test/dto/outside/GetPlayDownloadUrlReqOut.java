package org.bqf.test.dto.outside;

import org.bqf.test.dto.BaseReq;
import org.bqf.test.dto.Header;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 类描述: 本类是获取播放及下载地址外部请求
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayDownloadUrlReqOut extends BaseReq {

    private static final long serialVersionUID = 1L;
    
    @Builder
    public GetPlayDownloadUrlReqOut(Header header,String userId, String contentCode, String contentType) {
		super(header);
		this.userId = userId;
		this.contentCode = contentCode;
		this.contentType = contentType;
	}

	/**
     * 用户ID
     */
    private String userId;

    /**
     * 内容唯一标识
     */
    @NotBlank(message = "内容标识不能为空")
    private String contentCode;

    /**
     * 作品类型
     */
    private String contentType;

}
