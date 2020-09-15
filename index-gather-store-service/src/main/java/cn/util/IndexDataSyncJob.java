package cn.util;

import cn.hutool.core.date.DateUtil;
import cn.pojo.Index;
import cn.service.IndexDataService;
import cn.service.IndexService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author 胡建德
 * 定时器类
 */
public class IndexDataSyncJob extends QuartzJobBean {

    @Autowired
    private IndexService indexService;

    @Autowired
    private IndexDataService indexDataService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时启动：" + DateUtil.now());
        List<Index> indexes = indexService.fresh();
        for (Index index : indexes) {
            indexDataService.fresh(index.getCode());
        }
        System.out.println("定时结束：" + DateUtil.now());
    }
}
