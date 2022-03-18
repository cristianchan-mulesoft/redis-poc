/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package redis.internal.metadata;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import static org.mule.runtime.api.metadata.resolving.FailureCode.INVALID_METADATA_KEY;

public class RedisInputResolverWithKeyResolver implements TypeKeysResolver, InputTypeResolver<String> {

  public static final String REDIS_INPUT_RESOLVER_WITH_KEY_RESOLVER = "RedisInputResolverWithKeyResolver";

  @Override
  public MetadataType getInputMetadata(MetadataContext context, String key)
      throws MetadataResolvingException {
    final ObjectTypeBuilder objectBuilder = BaseTypeBuilder.create(JAVA).objectType();
    switch (key) {
      case "String":
        objectBuilder.addField().key("key").value().stringType();
        objectBuilder.addField().key("value").value().stringType();
        break;
      case "List":
      case "Set":
        objectBuilder.addField().key("key").value().stringType();
        objectBuilder.addField().key("values").value().arrayType().of().stringType();
        break;
      default:
        throw new MetadataResolvingException("Unknown key:" + key, INVALID_METADATA_KEY);
    }

    return objectBuilder.build();
  }

  @Override
  public Set<MetadataKey> getKeys(MetadataContext context) {
    List<String> types = Arrays.asList("String", "List", "Set");
    return types.stream().map(e -> MetadataKeyBuilder.newKey(e)
        .build()).collect(toSet());
  }

  @Override
  public String getResolverName() {
    return REDIS_INPUT_RESOLVER_WITH_KEY_RESOLVER;
  }

  @Override
  public String getCategoryName() {
    return "RedisMetadataExtensionResolver";
  }
}
