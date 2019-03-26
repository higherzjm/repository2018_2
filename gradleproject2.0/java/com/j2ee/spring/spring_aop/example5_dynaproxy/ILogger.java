package com.j2ee.spring.spring_aop.example5_dynaproxy;

import java.lang.reflect.Method;

public interface ILogger {
     void start(Method method);
      void end(Method method);
 }