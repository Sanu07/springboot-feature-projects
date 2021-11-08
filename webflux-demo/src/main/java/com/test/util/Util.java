package com.test.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.test.entity.MilestoneScopeData;
import com.test.enums.MilestoneEnum;

public class Util {

	public static void main(String[] args) {
		Util util = new Util();
		// util.changeListToMap();
		// util.checkReduce();
		util.checkDouble();
	}
	
	private void checkDouble() {
		double progress = 0.756;
		progress *= 100;
		DecimalFormat df = new DecimalFormat("#");
		BigDecimal bd = new BigDecimal(df.format(progress));
		System.out.println(df.format(progress));
		System.out.println(bd.intValue());
		long round = Math.round(progress);
		System.out.println(round);
	}

	private void changeListToMap() {
		List<MilestoneScopeData> list = Arrays.asList(
				MilestoneScopeData.builder().id(1).field("F1").vallue("V1").build(),
				MilestoneScopeData.builder().id(1).field("F1").vallue("V2").build(),
				MilestoneScopeData.builder().id(1).field("F2").vallue("V1").build(),
				MilestoneScopeData.builder().id(1).field("F2").vallue("V2").build(),
				MilestoneScopeData.builder().id(1).field("F3").vallue("V1").build(),
				MilestoneScopeData.builder().id(1).field("F3").vallue("V2").build(),
				MilestoneScopeData.builder().id(1).field("F3").vallue("V3").build()
				);
		
		checkValue(MilestoneScopeData.builder().build());
		List<MilestoneEnum> enumList = new ArrayList<>();
		enumList.add(MilestoneEnum.F1);
		enumList.add(MilestoneEnum.F2);
		enumList.remove(MilestoneEnum.F1);
		
		String s = null;
		MilestoneScopeData nullEnum = MilestoneScopeData.builder().field(s == null ? s : MilestoneEnum.valueOf(s).name()).build();
		nullEnum.setId(2);
		System.out.println(nullEnum);
		
		Map<MilestoneEnum, List<MilestoneEnum>> data = list.stream()
				.collect(Collectors.groupingBy(e -> MilestoneEnum.valueOf(e.getField()),
				Collectors.mapping(e -> MilestoneEnum.valueOf(e.getVallue()), Collectors.toList())));
		System.out.println(data);
		
	}

	private void checkValue(MilestoneScopeData milestoneScopeData) {
		System.out.println(milestoneScopeData);
		System.out.println(milestoneScopeData.getField());
		MilestoneScopeData milestone = MilestoneScopeData.builder().field(milestoneScopeData.getField()).vallue(null).build();
		System.out.println(milestone);
	}
	
	private void checkReduce() {
		Integer reduce = Arrays.asList(1, 2, 3, 4).parallelStream().reduce(10, (a, b) -> a + b,  (a, b) -> {
			return a + b;
		});
		System.out.println(reduce);
	}
}
