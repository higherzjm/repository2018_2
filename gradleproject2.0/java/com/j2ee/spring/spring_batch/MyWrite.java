package com.j2ee.spring.spring_batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("mywrite")
public class MyWrite implements ItemWriter<Map<String,Object>> {

    @Override
    public void write(List<? extends Map<String, Object>> list) throws Exception {
        System.out.println("write");
        System.out.println("list size:"+list.size());
        Map<String,Object> map=list.get(0);
        System.out.println("Write name:"+map.get("name"));
        System.out.println("Write age:"+map.get("age"));
    }
}