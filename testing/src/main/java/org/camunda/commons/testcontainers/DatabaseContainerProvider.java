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
package org.camunda.commons.testcontainers;

import org.testcontainers.containers.Db2Container;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

public class DatabaseContainerProvider {

  protected static JdbcDatabaseContainer dbContainer;

  static {
    Runtime.getRuntime().addShutdownHook(new Thread("Testcontainers cleanup") {
      @Override
      public void run() {
        DatabaseContainerProvider.dbContainer.stop();
      }
    });
  }

  public DatabaseContainerProvider() {
    this(null);
  }

  public DatabaseContainerProvider(String databaseType) {
    // use singleton pattern for container creation
    if (dbContainer == null) {
      dbContainer = createDbContainer(databaseType);
    }
  }

  protected JdbcDatabaseContainer createDbContainer(String databaseType) {
    databaseType = (databaseType != null)? databaseType : DatabaseImageResolver.getDatabaseType();
    String dbImageName = DatabaseImageResolver.getDockerImageNameForType(databaseType);

    switch (databaseType) {
      case "mysql":
        return new MySQLContainer(dbImageName);
      case "postgres":
        return new PostgreSQLContainer(dbImageName);
      case "mariadb":
        return new MariaDBContainer(dbImageName);
      case "mssql":
        return new MSSQLServerContainer(dbImageName);
      case "db2":
        return new Db2Container(dbImageName);
      case "oracle":
        return new OracleContainer(dbImageName);
      default:
        return null;
    }
  }

  protected void configureDbContainer() {
    String name = TestcontainersConfiguration.getInstance()
        .getProperties()
        .getProperty("db.name");
    String username = TestcontainersConfiguration.getInstance()
        .getProperties()
        .getProperty("db.username");
    String password = TestcontainersConfiguration.getInstance()
        .getProperties()
        .getProperty("db.password");

    // JdbcContainer conf
    if (name != null && username != null && password != null) {
      dbContainer.withDatabaseName(name)
                 .withUsername(username)
                 .withPassword(password);
    }

    // enable when it's properly working
    // dbContainer.withReuse(true);
  }

  public void startDatabase() {
    if (dbContainer != null) {
      configureDbContainer();
      dbContainer.start();
    }
  }

  public String getJdbcUrl() {
    return dbContainer.getJdbcUrl();
  }

  public JdbcDatabaseContainer getDbContainer() {
    return dbContainer;
  }
}