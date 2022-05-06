/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.metadata;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import static org.mule.runtime.api.metadata.resolving.FailureCode.INVALID_METADATA_KEY;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.model.ArrayType;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.impl.DefaultStringType;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;

public class RedisOutputAnyTypeResolver implements OutputTypeResolver<String> {

  public static final String TEST_OUTPUT_ANY_TYPE_RESOLVER = "RedisOutputAnyTypeResolver";
  public static final String METADATA_EXTENSION_RESOLVER = "RedisMetadataExtensionResolver";

  @Override
  public MetadataType getOutputType(MetadataContext context, String key)
      throws MetadataResolvingException {

    final DefaultStringType stringType = BaseTypeBuilder.create(JAVA).stringType().build();
    final ArrayType arrayType = BaseTypeBuilder.create(JAVA).arrayType().of(stringType).build();

    switch (key) {
      case "String":
        return stringType;
      case "List":
        return arrayType;
      case "Set":
        return arrayType;
      default:
        throw new MetadataResolvingException("Unknown key:" + key, INVALID_METADATA_KEY);
    }
  }

  @Override
  public String getCategoryName() {
    return METADATA_EXTENSION_RESOLVER;
  }

  @Override
  public String getResolverName() {
    return TEST_OUTPUT_ANY_TYPE_RESOLVER;
  }
}
