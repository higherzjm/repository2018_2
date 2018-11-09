package com.j2ee.spring.spring_batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("myprocess2")
public class myprocess2 implements ItemProcessor {

    @Override
    public Object process(Object o) throws Exception {
        Map<String,Object> map= (Map<String, Object>) o;
        System.out.println("rocess2 name:"+map.get("name"));
        System.out.println("rocess2 age:"+map.get("age"));
        return map;
    }
}