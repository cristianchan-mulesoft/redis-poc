/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package redis.internal.config;

import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.internal.connection.provider.RedisConnectionProvider;
import redis.internal.operation.RedisOperations;
import redis.internal.source.RedisSubSource;

@Configuration(name = "Jedis")
@Operations(RedisOperations.class)
@Sources(RedisSubSource.class)
@ConnectionProviders(RedisConnectionProvider.class)
public class RedisConfiguration implements Initialisable {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfiguration.class);

  @RefName
  private String configName;

  public String getConfigName() {
    return configName;
  }

  @Override
  public void initialise() throws InitialisationException {
    LOGGER.info("Initializing config with name: " + configName);
  }
}
