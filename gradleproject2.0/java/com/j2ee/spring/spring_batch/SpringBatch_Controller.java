package com.j2ee.spring.spring_batch;

import com.j2ee.netty.Netty_SimpleClient;
import com.j2ee.netty.Netty_SimpleClientHandler;
import com.j2ee.netty.Netty_SimpleServer;
import com.j2ee.spring.spring_util.SpringConfigTool;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zjm on 2018/11/6.
 */
@Controller
@RequestMapping(value ="/springbatchcontroller")
public class SpringBatch_Controller {
    @Resource(name="jobLauncher")
    private SimpleJobLauncher simpleJobLauncher;

    @Resource(name="jobParameterBulider")
    JobParametersBuilder jobParameterBulider;
    /**
     * 例1
     * @return
     */
    @RequestMapping(value = "/example1")
    @ResponseBody
    public  String example1() {
        try {
            System.out.println("------------------myFirstBatchJob-----------------");
            Job myFirstBatchJob= (Job) SpringConfigTool.getBean("mybatchjob");
            jobParameterBulider.addDate("date", new Date());
            JobExecution jobExecution= null;
            jobExecution = simpleJobLauncher.run(myFirstBatchJob, jobParameterBulider.toJobParameters());
            System.out.println("------------------myJob:"+jobExecution.getStatus());
            return "发起成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "连接失败";
        }
    }


}
