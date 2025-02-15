package com.dengenxi.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 *
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 20:13:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistLoginBO {
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号长度不规范")
    private String mobile;
    @NotBlank(message = "验证码不能为空")
    private String smsCode;
    private String nickname;
}
