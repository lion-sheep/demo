package com.example.elasticjoblitespringbootstarter.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiaoma
 * @desc
 * @date 2018/9/14 上午11:57
 */
@Setter
@Getter
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
