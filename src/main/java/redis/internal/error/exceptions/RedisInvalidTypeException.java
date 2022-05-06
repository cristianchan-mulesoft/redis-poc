/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.error.exceptions;

import org.mule.runtime.extension.api.exception.ModuleException;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import static redis.internal.error.RedisErrorType.INVALID_INPUT_TYPE;

/**
 * A {@link ModuleException} for the cases in which a invalid input type was provided.
 */
public class RedisInvalidTypeException extends ModuleException {

  /**
   * Returns an instance of {@link RedisInvalidTypeException}.
   * @param message a message that specifies the invalid type.
   */

  public RedisInvalidTypeException(String message) {
    super(createStaticMessage(message), INVALID_INPUT_TYPE);
  }
}
