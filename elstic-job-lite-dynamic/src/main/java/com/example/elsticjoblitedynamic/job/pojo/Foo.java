package com.example.elsticjoblitedynamic.job.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/14 上午11:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foo implements Serializable {
    private static final long serialVersionUID = -8517788534790566196L;
    private Long id;
    private String location;
    private Status status;


    enum Status {
        TODO, COMPLETED;
    }
}
