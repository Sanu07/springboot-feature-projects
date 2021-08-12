package com.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.batch.model.Customer;

public class CustomerPostgresMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		customer.setAddress(rs.getString("customer_address"));
		customer.setCreatedAt((rs.getTimestamp("created_at").toLocalDateTime()));
		customer.setCreatedByUserId(rs.getString("created_by_user_id"));
		customer.setCustomerId(rs.getString("customer_id"));
		customer.setCustomerName(rs.getString("customer_name"));
		customer.setEmail(rs.getString("customer_email"));
		customer.setId(getUUID(rs.getBytes("_id")));
		customer.setPhone(rs.getString("customer_phone"));
		customer.setStatus(rs.getBoolean("is_active"));
		customer.setUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());
		customer.setVersion(rs.getInt("version"));
		return customer;
	}
	
	private UUID getUUID(byte[] bytes) {
		if (bytes.length != 16) {
	        throw new IllegalArgumentException();
	    }
	    int i = 0;
	    long msl = 0;
	    for (; i < 8; i++) {
	        msl = (msl << 8) | (bytes[i] & 0xFF);
	    }
	    long lsl = 0;
	    for (; i < 16; i++) {
	        lsl = (lsl << 8) | (bytes[i] & 0xFF);
	    }
	    return new UUID(msl, lsl);
	}

}
