/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.connection;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

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
  public void connect() {
    jedis.connect();
  }

  @Override
  public void disconnect() {
    jedis.disconnect();
  }

  @Override
  public void subscribe(final JedisPubSub jedisPubSub, final String channel) {
    jedis.subscribe(jedisPubSub, channel);
  }

  @Override
  public boolean isConnected() {
    return jedis.isConnected();
  }

  @Override
  public void set(final String key, final String value) {
    jedis.set(key, value);
  }

  @Override
  public void setList(final String key, final String[] values) {
    jedis.lpush(key, values);
  }

  @Override
  public void setSet(final String key, final String[] values) {
    jedis.sadd(key, values);
  }

  @Override
  public String get(final String key) {
    return jedis.get(key);
  }

  @Override
  public List<String> getList(final String key) {
    final long size = jedis.llen(key);
    return jedis.lrange(key, 0, size);
  }

  @Override
  public Set<String> getSet(final String key) {
    return jedis.smembers(key);
  }

  @Override
  public String toString() {
    return "RedisJedisConnection{" +
        "host='" + host + '\'' +
        ", port=" + port +
        ", timeout=" + timeout +
        ", jedis=" + jedis +
        '}';
  }
}
