package org.bqf.test.dto.outside;

import org.bqf.test.dto.BaseRsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 类描述: 本类是获取播放及下载地址响应
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayDownloadUrlRspOut extends BaseRsp {

    private static final long serialVersionUID = 1L;

    private String downloadUrl;
}
