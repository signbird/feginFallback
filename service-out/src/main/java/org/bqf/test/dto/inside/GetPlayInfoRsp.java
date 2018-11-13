package org.bqf.test.dto.inside;

import org.bqf.test.dto.BaseRsp;
import org.bqf.test.dto.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 消息体类
 * 项目名称: cartoon
 * 包: com.migu.rstone.dto.inside.content
 * 类名称: GetPlayDownloadRsp.java
 * 类描述: 本类是获取播放及下载地址响应
 * 创建人: chensizhuo
 * 创建时间: 2017/8/2 16:55
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayInfoRsp extends BaseRsp{
    
    private static final long serialVersionUID = 1L;

	public GetPlayInfoRsp(Result result,String playInfo) {
		super(result);
		this.playInfo = playInfo;
	}

	/**
     * 内容的下载或者播放信息描述
     */
	private String playInfo;
}
