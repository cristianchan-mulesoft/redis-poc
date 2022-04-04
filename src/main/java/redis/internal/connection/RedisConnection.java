/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.connection;

import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Set;

public interface RedisConnection {

  void connect();

  void disconnect();

  void subscribe(final JedisPubSub jedisPubSub, final String channel);

  boolean isConnected();

  void set(String key, String value);

  void setList(String key, String[] values);

  void setSet(String key, String[] values);

  void publish(String channel, String message);

  String get(String key);

  List<String> getList(String key);

  Set<String> getSet(String key);
}
