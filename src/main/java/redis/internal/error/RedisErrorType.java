/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.error;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

public enum RedisErrorType implements ErrorTypeDefinition<RedisErrorType> {
  INVALID_INPUT_TYPE(MuleErrors.TRANSFORMATION);

  private ErrorTypeDefinition<? extends Enum<?>> parent;

  RedisErrorType(ErrorTypeDefinition<? extends Enum<?>> parent) {
    this.parent = parent;
  }
}
