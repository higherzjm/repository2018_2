package com.j2ee.spring.spring_batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("mywrite2")
public class MyWrite2 implements ItemWriter<Map<String,Object>> {

    @Override
    public void write(List<? extends Map<String, Object>> list) throws Exception {
        System.out.println("write2");
        System.out.println("list2 size:"+list.size());
        Map<String,Object> map=list.get(0);
        System.out.println("Write2 name:"+map.get("name"));
        System.out.println("write2 age:"+map.get("age"));
    }
}