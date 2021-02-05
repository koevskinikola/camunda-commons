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

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.PostgreSQLContainerProvider;
import org.testcontainers.utility.DockerImageName;

public class CamundaPostgreSQLContainerProvider extends PostgreSQLContainerProvider {

  protected static final String NAME = "campostgresql";

  @Override
  public boolean supports(String databaseType) {
    return NAME.equals(databaseType);
  }

  @Override
  public JdbcDatabaseContainer newInstance(String tag) {
    DockerImageName dockerImageName = TestcontainersHelper
      .resolveDockerImageName("postgresql", tag, "postgres");
    return new PostgreSQLContainer(dockerImageName);
  }
}