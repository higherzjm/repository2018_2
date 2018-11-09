package com.j2ee.spring.spring_batch;

import org.springframework.batch.item.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("myread2")
public class Myread2 implements ItemReader<Map<String,Object>>,ItemStream {
    private int currentLocation=0;
    private static final String CURRENT_LOCATION="current.location";
    @Override
    public Map<String, Object> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentLocation>0){
            return  null;
        }else {
            currentLocation++;
            System.out.println("read");
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("name", "张三2");
            retMap.put("age", "30");
            return retMap;
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("open2");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("update2");
        executionContext.putLong(CURRENT_LOCATION,new Long(currentLocation).longValue());
    }

    @Override
    public void close() throws ItemStreamException {
        System.out.println("close2");
    }
}