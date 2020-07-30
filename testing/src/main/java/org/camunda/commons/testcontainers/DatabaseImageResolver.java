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

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.utility.TestcontainersConfiguration;

public class DatabaseImageResolver {

  protected static final Map<String, String> DEFAULT_IMAGES =  new HashMap<>(7);
  protected static final String DEFAULT_MYSQL_IMAGE = "mysql:5.7.22";
  protected static final String DEFAULT_MARIADB_IMAGE = "mariadb:10.3.6";
  protected static final String DEFAULT_POSTGRESQL_IMAGE = "postgres:9.6.12";
  protected static final String DEFAULT_MSSQL_IMAGE = "mcr.microsoft.com/mssql/server:2017-CU12";
  protected static final String DEFAULT_DB2_IMAGE = "ibmcom/db2:11.5.0.0a";
  protected static final String DEFAULT_ORACLE_IMAGE = "registry.camunda.com/camunda-ci-oracle:18";
  protected static final String DEFAULT_CRDB_IMAGE = "cockroachdb/cockroach:v20.1.3";

  static {
    DEFAULT_IMAGES.put("mysql", DEFAULT_MYSQL_IMAGE);
    DEFAULT_IMAGES.put("mariadb", DEFAULT_MARIADB_IMAGE);
    DEFAULT_IMAGES.put("postgres", DEFAULT_POSTGRESQL_IMAGE);
    DEFAULT_IMAGES.put("mssql", DEFAULT_MSSQL_IMAGE);
    DEFAULT_IMAGES.put("oracle", DEFAULT_ORACLE_IMAGE);
    DEFAULT_IMAGES.put("db2", DEFAULT_DB2_IMAGE);
    DEFAULT_IMAGES.put("cockroachdb", DEFAULT_CRDB_IMAGE);
  }

  protected static final DatabaseImageResolver INSTANCE = new DatabaseImageResolver();

  private DatabaseImageResolver() {
  }

  public static String getDockerImageNameForType(String databaseType) {
    if (DEFAULT_IMAGES.containsKey(databaseType)) {
      return TestcontainersConfiguration
          .getInstance()
          .getProperties()
          .getProperty("db.container.image", DEFAULT_IMAGES.get(databaseType));
    } else {
      return null;
    }
  }

  public static String getDatabaseType() {
    return TestcontainersConfiguration
        .getInstance()
        .getProperties()
        .getProperty("db.type");
  }

  public static DatabaseImageResolver getInstance() {
    return INSTANCE;
  }
}