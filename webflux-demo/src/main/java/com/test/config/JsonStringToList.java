package com.test.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.entity.Tracker;

@ReadingConverter
public class JsonStringToList implements Converter<String, List<Tracker>> {
	
	@Autowired
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<Tracker> convert(String source) {
		List<Tracker> nriData = null;
		try {
			nriData = mapper.readValue(source, List.class);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return nriData;
	}

}
