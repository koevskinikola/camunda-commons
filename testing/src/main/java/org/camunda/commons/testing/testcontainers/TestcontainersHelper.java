/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.commons.testing.testcontainers;

import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestcontainersHelper {

  public static String getRegistryUrl() {
    String registryUrl = (String) TestcontainersConfiguration.getInstance().getProperties().getOrDefault("docker.registry.url", "");
    if (StringUtils.isEmpty(registryUrl)) {
      return StringUtils.EMPTY;
    }
    return appendIfMissing(registryUrl, "/");
  }

  public static String resolveImageName(String imageProperty, String defaultImage) {
    String image = TestcontainersConfiguration.getInstance().getEnvVarOrProperty(imageProperty, defaultImage);
    if (image == null) {
      throw new IllegalStateException("An image to use for Oracle containers must be configured. " +
          "To do this, please place a file on the classpath named `testcontainers.properties`, " +
          "containing `" + imageProperty + "=IMAGE`, where IMAGE is a suitable image name and tag.");
    } else {
      return image;
    }
  }

  public static DockerImageName resolveDockerImageName(String dbLabel, String tag, String defaultDbImage) {
    String registryUrl = getRegistryUrl();
    String imageProperty = dbLabel + ".container.image";
    String postgresqlImage = resolveImageName(imageProperty, defaultDbImage) + ":" + tag;
    return DockerImageName.parse( registryUrl + postgresqlImage)
      .asCompatibleSubstituteFor(defaultDbImage);
  }

  protected static String appendIfMissing(String str, String suffix) {
    if (str == null || suffix == null || suffix.isEmpty()) {
      return str;
    }
    return str + suffix;
  }
}