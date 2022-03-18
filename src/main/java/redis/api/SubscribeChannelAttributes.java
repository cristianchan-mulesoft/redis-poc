/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.api;

import java.io.Serializable;

public class SubscribeChannelAttributes implements Serializable {

  private String channel;

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SubscribeChannelAttributes that = (SubscribeChannelAttributes) o;

    return channel != null ? channel.equals(that.channel) : that.channel == null;

  }

  @Override
  public int hashCode() {
    return channel != null ? channel.hashCode() : 0;
  }
}
