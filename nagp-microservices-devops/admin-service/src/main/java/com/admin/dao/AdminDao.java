package com.admin.dao;

import com.admin.model.Admin;

public interface AdminDao extends GetAndSaveDao<Admin, Long>, DeleteDao<Long> {

}
