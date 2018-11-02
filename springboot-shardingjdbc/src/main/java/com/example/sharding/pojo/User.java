package com.example.sharding.pojo;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/7 上午1:07
 */
public class User implements Serializable {
    private static final long serialVersionUID = -5829309308907817286L;
    private String name;
    private String card;

    public User() {
    }

    public User(String name, String card) {
        this.name = name;
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("card = " + card)
                .add("name = " + name)
                .toString();
    }
}
