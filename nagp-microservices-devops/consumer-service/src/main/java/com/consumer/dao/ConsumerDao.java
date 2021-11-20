package com.consumer.dao;

import com.consumer.model.Consumer;

public interface ConsumerDao extends DeleteDao<Long>, GetAndSaveDao<Consumer, Long> {

}
