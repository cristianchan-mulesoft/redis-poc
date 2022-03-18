/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.connection;


import java.util.List;
import java.util.Set;

public interface RedisConnection {

  void set(String value);

  void set(List<String> values);

  void set(Set<String> values);

  String get(String key);

  List<String> getList(String key);

  Set<String> getSet(String key);
}
