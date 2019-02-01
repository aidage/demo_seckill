package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception{
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception{
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}" +seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception{
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}" + exposer);
    }

    @Test
    public void testExecuteSeckill() throws Exception{
        long id = 1000;
        long phone = 15175880883L;
        String md5 = "472bc453dcf9f5463072454024db2540";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
            logger.info("result=" + execution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage());
        }catch (SeckillCloseException e){
            logger.error(e.getMessage());
        }
    }

    // 集成测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogin() throws Exception{
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}" + exposer);
            long phone = 15175880883L;
            String md5 = "472bc453dcf9f5463072454024db2540";
            try {
                SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
                logger.info("result=" + execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
        }else {
            // 秒杀未开启
            logger.warn("exposer={}" + exposer);
        }
    }

}