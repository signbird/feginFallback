package org.bqf.test.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述: 返回结果封装对象,包含结果编码，结果描述.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "result")
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "返回码不能为空")
    private String resultCode;
    private String resultMessage;
}
