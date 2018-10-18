package com.secoo.send_message.pojo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/25 上午11:11
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = -6725241626639390695L;
    private String appId;
    private String openId;
    private Integer messageType;
    private Integer usableNum;
    private LocalDateTime createdTime;
}
