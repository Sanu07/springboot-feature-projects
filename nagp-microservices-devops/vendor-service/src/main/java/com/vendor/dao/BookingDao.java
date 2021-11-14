package com.vendor.dao;

import com.vendor.model.BookingDetails;

public interface BookingDao extends GetAndSaveDao<BookingDetails, Long>, DeleteDao<Long> {

}
