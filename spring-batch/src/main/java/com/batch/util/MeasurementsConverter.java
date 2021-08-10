package com.batch.util;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.model.Measurements;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeasurementsConverter implements AttributeConverter<List<Measurements>, String> {

	private final Logger logger = LoggerFactory.getLogger(MeasurementsConverter.class);

	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Measurements> measurementsInfo) {
		String measurementsInfoJson = null;
		try {
			measurementsInfoJson = objectMapper.writeValueAsString(measurementsInfo);
		} catch (final JsonProcessingException e) {
			logger.error("JSON parsing error", e);
		}
		return measurementsInfoJson;
	}

	@Override
	public List<Measurements> convertToEntityAttribute(String measurementsInfoJson) {
		List<Measurements> measurementsInfo = null;
		try {
			measurementsInfo = objectMapper.readValue(measurementsInfoJson, List.class);
		} catch (final IOException e) {
			logger.error("JSON reading error", e);
		}
		return measurementsInfo;
	}
}
