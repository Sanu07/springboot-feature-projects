package com.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.batch.model.Customer;
import com.batch.model.Feedback;
import com.batch.model.Order;

public class FeedbackPostgresMapper implements RowMapper<Feedback> {

	@Override
	public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feedback feedback = new Feedback();
		feedback.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		Customer customer = new Customer();
		customer.setId(getUUID(rs.getBytes("customer_id")));
		feedback.setCustomer(customer);
		feedback.setDescription(rs.getString("description"));
		feedback.setId(getUUID(rs.getBytes("_id")));
		Order order = new Order();
		order.setId(getUUID(rs.getBytes("order_id")));
		feedback.setOrder(order);
		feedback.setRating(rs.getInt("rating"));
		feedback.setStatus(rs.getBoolean("is_active"));
		feedback.setUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());
		return feedback;
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
