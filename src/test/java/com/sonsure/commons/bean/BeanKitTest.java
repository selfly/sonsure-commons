/*
 * Copyright (c) 2020. www.sonsure.com Inc. All rights reserved.
 * You may obtain more information at
 *
 *   http://www.sonsure.com
 *
 * Designed By Selfly Lee (selfly@live.com)
 */

package com.sonsure.commons.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class BeanKitTest {

    private User user;

    @Before
    public void before() {
        user = new User();
        user.setUserId(10000L);
        user.setUsername("selfly");
        user.setPassword("123456");
        user.setAge(18);
        user.setAmount(new BigDecimal("10086"));
        user.setStatus("3");
        user.setGmtCreate(new Date());
    }

    @Test
    public void baseCopy() {
        final UserVo userVo = BeanKit.copyProperties(new UserVo(), user);
        Assert.assertEquals(userVo.getUserId(), Integer.valueOf(user.getUserId().toString()));
        Assert.assertEquals(userVo.getUsername(), user.getUsername());
        Assert.assertEquals(userVo.getPassword(), user.getPassword());
        Assert.assertEquals(userVo.getAge(), 18);
        Assert.assertEquals(userVo.getAmount(), Long.valueOf(10086L));
        Assert.assertEquals(userVo.getStatus(), UserVo.Status.DISABLE_LOGIN);
        Assert.assertNotNull(userVo.getGmtCreate());
    }

    @Test
    public void StringToInteger() {
        user.setYear("5");
        final UserVo userVo = BeanKit.copyProperties(new UserVo(), user);
        Assert.assertEquals(5, (int) userVo.getYear());
    }
}
