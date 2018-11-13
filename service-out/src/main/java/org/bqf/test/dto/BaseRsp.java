package org.bqf.test.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;

/**
 * 类描述: response基类
 */
@Data
@NonFinal
@NoArgsConstructor
@AllArgsConstructor
public class BaseRsp implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "结果信息不能为空")
	@Valid
	private Result result;
}
