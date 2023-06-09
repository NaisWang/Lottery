package cn.tedus.lottery.test;

import cn.itedus.lottery.infrastructure.dao.IActivityDao;
import cn.itedus.lottery.infrastructure.po.Activity;
import cn.itedus.lottery.rpc.IActivityBooth;
import cn.itedus.lottery.rpc.req.ActivityReq;
import cn.itedus.lottery.rpc.res.ActivityRes;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author : whz
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = cn.itedus.lottery.LotteryApplication.class)
public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Resource
    IActivityDao activityDao;

    @Reference(interfaceClass = IActivityBooth.class, url = "dubbo://127.0.0.1:20880")
    private IActivityBooth activityBooth;

    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100005L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("xiaofuge");
        activityDao.insert(activity);
    }

    @Test
    public void test_rpc() {
        ActivityReq req = new ActivityReq();
        req.setActivityId(100001L);
        ActivityRes res = activityBooth.queryActivityById(req);
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }

}
