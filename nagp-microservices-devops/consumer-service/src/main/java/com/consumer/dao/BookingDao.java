package com.consumer.dao;

import com.consumer.model.BookingDetails;

public interface BookingDao extends GetAndSaveDao<BookingDetails, Long>, DeleteDao<Long> {

}
