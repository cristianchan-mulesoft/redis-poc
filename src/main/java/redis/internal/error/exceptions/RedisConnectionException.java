/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.error.exceptions;


import org.mule.runtime.extension.api.exception.ModuleException;
import redis.internal.error.RedisErrorType;

import static redis.internal.error.RedisErrorType.CONNECTIVITY;

public class RedisConnectionException extends ModuleException {

  public RedisConnectionException(String message, Throwable cause) {
    super(message, CONNECTIVITY);
  }
}
