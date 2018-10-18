package com.secoo.send_message.mapper;

import com.secoo.send_message.pojo.Message;

public interface MessageMapper {
    int deleteMessage(Message record);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}