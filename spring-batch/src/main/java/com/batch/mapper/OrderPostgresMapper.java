package com.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.batch.model.Customer;
import com.batch.model.Measurements;
import com.batch.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderPostgresMapper implements RowMapper<Order> {

	private ObjectMapper mapper = new ObjectMapper();
	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		order.setCreatedAt((rs.getTimestamp("created_at").toLocalDateTime()));
		order.setId(getUUID(rs.getBytes("_id")));
		order.setStatus(rs.getBoolean("is_active"));
		order.setUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());
		order.setVersion(rs.getInt("version"));
		Customer customer = new Customer();
		customer.setId(getUUID(rs.getBytes("customer_id")));
		order.setCustomer(customer);
		try {
			order.setMeasurements(mapper.readValue(rs.getString("measurements"), new TypeReference<List<Measurements>>() {}));
		} catch (JsonProcessingException | SQLException e) {
			e.printStackTrace();
		}
		order.setEstimatedDeliveryDate(rs.getTimestamp("estimated_delivery_date").toLocalDateTime());
		if (rs.getTimestamp("order_delivered_on") != null) {
			order.setOrderDeliveredOn(rs.getTimestamp("order_delivered_on").toLocalDateTime());
		}
		order.setTotalAmount(rs.getDouble("total_amount"));
		return order;
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
