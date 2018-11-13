/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package org.bqf.test.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;

/**
 * 项目名称: Rainbow Stone for cartoon
 * 包: com.migu.rstone.dto
 * 类描述: request基类
 * 创建人: baiqiufei
 * 创建时间: 2017/8/29 17:59
 */
@Data
@NonFinal
@NoArgsConstructor
@AllArgsConstructor
public class BaseReq implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull(message = "请求头不能为空")
	@Valid
	private Header requestHeader;
}
