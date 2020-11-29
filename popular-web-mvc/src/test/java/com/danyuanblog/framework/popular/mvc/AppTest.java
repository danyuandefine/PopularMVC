package com.danyuanblog.framework.popular.mvc;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.danyuanblog.framework.popularmvc.dto.ApiInfo;
import com.danyuanblog.framework.popularmvc.startup.Startup;
import com.danyuanblog.framework.popularmvc.utils.SpringBeanUtil;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes=Startup.class)
@RunWith(SpringRunner.class)
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test
    public void testLocale(){
    	System.out.println(Locale.US.toString());
    }
    
    @Test
    public void testBeanUtil(){
    	ApiInfo info = new ApiInfo();
    	info.setApiName("test");
    	SpringBeanUtil.addExistSingletonBean(info);
    	info = SpringBeanUtil.getBean(ApiInfo.class);    	
    }
}
