/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.connection.provider;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.internal.connection.param.RedisParams;

import static org.mule.runtime.extension.api.annotation.param.ParameterGroup.CONNECTION;

public class RedisConnectionProvider implements CachedConnectionProvider<Jedis> {

  private final Logger LOGGER = LoggerFactory.getLogger(RedisConnectionProvider.class);

  @ParameterGroup(name = CONNECTION)
  @Placement(order = 1)
  private RedisParams connectionParams;

  @Override
  public Jedis connect() throws ConnectionException {
    LOGGER.info("Redis connector ready, host: {}, port: {}",
                connectionParams.getHost(),
                connectionParams.getPort());

    Jedis jedis = new Jedis(connectionParams.getHost(), connectionParams.getPort(), connectionParams.getTimeout());

    try {
      jedis.connect();
    } catch (final JedisConnectionException exception) {
      throw new ConnectionException("Unable to connect to redis host : " + connectionParams.getHost() + " port : "
          + connectionParams.getPort(), exception);
    }

    return jedis;
  }

  @Override
  public void disconnect(Jedis connection) {
    try {
      connection.disconnect();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting   [" + connectionParams.getHost() + " " + connectionParams.getPort() + "]: "
          + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(Jedis connection) {
    final boolean connectionStatus = connection.getConnection().isConnected();
    if (connectionStatus) {
      return ConnectionValidationResult.success();
    } else {
      return ConnectionValidationResult.failure("Error to connect to redis host",
                                                new ConnectionException("Unable to establish connection to redis : "
                                                    + connection.getConnection().toString()));
    }
  }
}
