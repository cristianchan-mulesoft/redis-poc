/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package redis.internal.operation;

import java.util.Map;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.internal.metadata.RedisInputResolverWithKeyResolver;
import redis.internal.metadata.RedisOutputAnyTypeResolver;
import redis.internal.metadata.model.ListType;
import redis.internal.metadata.model.StringType;

public class RedisOperations {

  private final ObjectMapper mapper = new ObjectMapper();

  public void set(@MetadataKeyId(RedisInputResolverWithKeyResolver.class) String type,
                  @Content @TypeResolver(RedisInputResolverWithKeyResolver.class) Map<String, Object> content,
                  @Connection Jedis connection)
      throws ConnectionException {

    try {
      if (type.equals("String")) {
        final StringType input = mapper.convertValue(content, StringType.class);
        final String key = input.getKey();
        final String value = input.getValue();

        connection.set(key, value);
      } else if (type.equals("List")) {
        final ListType input = mapper.convertValue(content, ListType.class);
        final String key = input.getKey();
        final String[] values = input.getValues();

        connection.lpush(key, values);
      } else if (type.equals("Set")) {
        final ListType input = mapper.convertValue(content, ListType.class);
        final String key = input.getKey();
        final String[] values = input.getValues();

        connection.sadd(key, values);
      }
    } catch (final JedisConnectionException connectionException) {
      throw new ConnectionException("Unable to connect to redis host : " + connection.getConnection().toString());
    }

  }

  @OutputResolver(output = RedisOutputAnyTypeResolver.class)
  @MediaType(value = MediaType.ANY, strict = false)
  public Object get(@Connection Jedis connection,
                    String key,
                    @MetadataKeyId(RedisInputResolverWithKeyResolver.class) String type) {


    if (type.equals("String")) {
      return connection.get(key);
    } else if (type.equals("List")) {
      final long size = connection.llen(key);
      return connection.lrange(key, 0, size);
    } else if (type.equals("Set")) {
      return connection.smembers(key);
    }

    return null;
  }
}
