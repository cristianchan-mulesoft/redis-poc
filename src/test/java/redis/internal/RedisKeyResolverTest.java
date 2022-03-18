/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.module.extension.internal.metadata.DefaultMetadataContext;

import static org.junit.Assert.*;

//@RunWith(MockitoJUnitRunner.class)
//public class RedisKeyResolverTest {
//
//  private RedisKeyResolver subject;
//
//  @Before
//  public void setUp() {
//    subject = new RedisKeyResolver();
//  }
//
//  @Test
//  public void getKeys() throws MetadataResolvingException, ConnectionException {
//    subject.getKeys(null);
//  }
//
//}
