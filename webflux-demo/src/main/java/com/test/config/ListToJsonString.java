package com.test.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.entity.Tracker;

@WritingConverter
public class ListToJsonString implements Converter<List<Tracker>, String> {
	
	@Autowired
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convert(List<Tracker> source) {
		String jsonData = null;
		try {
			jsonData = mapper.writeValueAsString(source);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

}
