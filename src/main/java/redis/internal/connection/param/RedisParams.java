/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package redis.internal.connection.param;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

import java.util.Objects;

public class RedisParams {

  @Parameter
  @Optional(defaultValue = "localhost")
  @Placement(tab = Placement.CONNECTION_TAB, order = 1)
  private String host;

  @Parameter
  @Optional(defaultValue = "6379")
  @Placement(tab = Placement.CONNECTION_TAB, order = 2)
  private int port;

  @Parameter
  @Optional(defaultValue = "30")
  @Placement(tab = Placement.CONNECTION_TAB, order = 3)
  private int timeout;

  public String getHost() {
    return host;
  }


  public int getPort() {
    return port;
  }

  public int getTimeout() {
    return timeout;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    RedisParams that = (RedisParams) o;
    return super.equals(o) &&
        Objects.equals(host, that.host) &&
        Objects.equals(port, that.port);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, port, super.hashCode());
  }
}
