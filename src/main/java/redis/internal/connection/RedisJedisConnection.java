/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.connection;

import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RedisJedisConnection implements RedisConnection {

  private String host;
  private int port;
  private int timeout;
  private Jedis jedis;


  public RedisJedisConnection(final String host, final int port, final int timeout) {
    this.host = host;
    this.port = port;
    this.timeout = timeout;
    this.jedis = new Jedis(host, port, timeout);
  }


  @Override
  public void set(String value) {

  }

  @Override
  public void set(List<String> values) {

  }

  @Override
  public void set(Set<String> values) {

  }

  @Override
  public String get(String key) {
    return null;
  }

  @Override
  public List<String> getList(String key) {
    return null;
  }

  @Override
  public Set<String> getSet(String key) {
    return null;
  }
}
