/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.source;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.api.SubscribeChannelAttributes;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.internal.connection.RedisConnection;

@DisplayName("On New Message")
@Alias("subscribe")
@MediaType(value = MediaType.ANY, strict = false)
public class RedisSubSource extends Source<String, SubscribeChannelAttributes> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisSubSource.class);

  /**
   * Redis channel name to listen.
   */
  @Parameter
  private String channel;

  @Connection
  private ConnectionProvider<RedisConnection> connectionProvider;

  private JedisPubSub jedisPubSub;

  private RedisConnection connection;

  @Override
  public void onStart(final SourceCallback<String, SubscribeChannelAttributes> sourceCallback) throws MuleException {
    LOGGER.info("Starting redis listener {} ", channel);
    connection = connectionProvider.connect();

    try {
      jedisPubSub = new JedisPubSub() {

        @Override
        public void onMessage(final String channel, final String message) {
          LOGGER.info("Message received {} for channel {} ", message, channel);
          final SubscribeChannelAttributes subAttributes = new SubscribeChannelAttributes();
          subAttributes.setChannel(channel);
          sourceCallback.handle(Result.<String, SubscribeChannelAttributes>builder()
              .output(message)
              .attributes(subAttributes)
              .build());
          LOGGER.info("Message send it {} for channel {} ", message, channel);
        }
      };

      connection.subscribe(jedisPubSub, channel);
    } catch (final JedisConnectionException exception) {
      sourceCallback.onConnectionException(new ConnectionException("Unable to connect to redis  : " + connection.toString()));
    } catch (final Exception exception) {
      LOGGER.error("Unexpected error ", exception);
      throw new MuleRuntimeException(exception);
    }
  }

  @Override
  public void onStop() {
    if (jedisPubSub.isSubscribed()) {
      LOGGER.info("Unsubscribing channel: {}", channel);
      jedisPubSub.unsubscribe();
      connectionProvider.disconnect(connection);
      LOGGER.info("Successfully unsubscribed from channel {}", channel);
    }
  }
}
