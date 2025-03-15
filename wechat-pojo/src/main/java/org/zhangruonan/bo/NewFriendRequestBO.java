package org.zhangruonan.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 *
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:17:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewFriendRequestBO {

    @NotBlank(message = "申请用户id不能为空")
    private String myId;

    @NotBlank(message = "想添加的用户id不能为空")
    private String friendId;

    private String friendRemark;

    @NotBlank(message = "申请理由不能为空")
    private String verifyMessage;

}
