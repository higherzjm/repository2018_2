package com.j2ee.spring.spring_quart;

import com.j2ee.spring.spring_util.SpringConfigTool;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zjm on 2018/11/8.
 */
@Component("srpingquarttotatchbean")
public class springQuartToBatchBean {
    @Resource(name="jobLauncher")
    private SimpleJobLauncher simpleJobLauncher;

    @Resource(name="jobParameterBulider")
    JobParametersBuilder jobParameterBulider;

    public void springQuartToBatchMethod() throws Exception{
        System.out.println("------------------myFirstBatchJob-----------------");
        Job myFirstBatchJob= (Job) SpringConfigTool.getBean("mybatchjob");
        jobParameterBulider.addDate("date", new Date());
        org.springframework.batch.core.JobExecution jobExecution= null;
        jobExecution = simpleJobLauncher.run(myFirstBatchJob, jobParameterBulider.toJobParameters());
       System.out.println("------------------myJob:"+jobExecution.getStatus());
    }
}
