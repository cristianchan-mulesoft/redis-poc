/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package redis.internal.operation;

import java.util.Map;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.metadata.TypeResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.internal.connection.RedisConnection;
import redis.internal.error.RedisErrorTypeProvider;
import redis.internal.error.exceptions.RedisInvalidTypeException;
import redis.internal.metadata.RedisInputResolverWithKeyResolver;
import redis.internal.metadata.RedisOutputAnyTypeResolver;
import redis.internal.metadata.model.ListType;
import redis.internal.metadata.model.StringType;

/**
 *  Redis is an open-source, networked, in-memory, persistent, journaled, key-value data store. Provides Redis connectivity to Mule:
 *  <ul>
 *  <li>Supports Redis Subscribe model for asynchronous message exchanges,</li>
 *  <li>Allows direct reading and writing operations in Redis collections,</li>
 *  </ul>
 *
 *   @author MuleSoft, Inc.
 */
public class RedisOperations {

  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Set the key and value in redis.
   * <p>
   *
   * @param type        The input content type
   * @param content     The key and the value to store
   * @param connection  Connection to redis
   */
  @Throws(RedisErrorTypeProvider.class)
  public void set(@MetadataKeyId(RedisInputResolverWithKeyResolver.class) final String type,
                  @Content @TypeResolver(RedisInputResolverWithKeyResolver.class) final Map<String, Object> content,
                  @Connection final RedisConnection connection)
      throws ConnectionException {

    try {
      switch (type) {
        case "String": {
          final StringType input = mapper.convertValue(content, StringType.class);
          final String key = input.getKey();
          final String value = input.getValue();

          connection.set(key, value);
          break;
        }
        case "List": {
          final ListType input = mapper.convertValue(content, ListType.class);
          final String key = input.getKey();
          final String[] values = input.getValues();

          connection.setList(key, values);
          break;
        }
        case "Set": {
          final ListType input = mapper.convertValue(content, ListType.class);
          final String key = input.getKey();
          final String[] values = input.getValues();

          connection.setSet(key, values);
          break;
        }

        default:
          throw new RedisInvalidTypeException("Unsupported input type :" + type);

      }
    } catch (final JedisConnectionException connectionException) {
      throw new ConnectionException("Unable to connect to redis : " + connection.toString());
    }
  }

  /**
   * Set the value stored in redis.
   * <p>
   *
   * @param type        The input content type
   * @param connection  Connection to redis
   * @param key         Key of the value to return
   * @return If the key already exists and ifNotExists is true, null is returned. Otherwise the message is returned.
   */
  @OutputResolver(output = RedisOutputAnyTypeResolver.class)
  @MediaType(value = MediaType.ANY, strict = false)
  @Throws(RedisErrorTypeProvider.class)
  public Object get(@Connection final RedisConnection connection,
                    final String key,
                    @MetadataKeyId(RedisInputResolverWithKeyResolver.class) final String type) {


    switch (type) {
      case "String":
        return connection.get(key);
      case "List":
        return connection.getList(key);
      case "Set":
        return connection.getSet(key);
      default:
        throw new RedisInvalidTypeException("Unsupported output type :" + type);
    }
  }
}
