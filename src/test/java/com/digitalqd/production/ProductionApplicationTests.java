package com.digitalqd.production;

import java.io.Serializable;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.digitalqd.production.utils.JsonConvert;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
class ProductionApplicationTests {

	@Autowired
	JsonConvert jsonConvert;

	private static class TempClass implements Serializable{
        private String dataValue;
        private String maxAccuValue;
        private String minAccuValue;
        private String avgAccuValue;
        private String recordTime;
    }
	
	@Test
	void contextLoads() {
		String str = "[{\"RecordTime\": \"1677600000000\", \"DataValue\": \"0\", \"MaxAccuValue\": \"0.002\", \"MinAccuValue\": \"0.002\", \"AvgAccuValue\": \"0.002\"}]";
        List<TempClass> result = jsonConvert.jsonToType(str, new TypeReference<List<TempClass>>() {
        });
        System.out.println(result.get(0).toString());
	}

}
