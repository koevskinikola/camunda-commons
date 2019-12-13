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
package org.camunda.commons.testconainers;

import org.testcontainers.utility.TestcontainersConfiguration;

public class DatabaseImageResolver {

  protected static final String DEFAULT_MYSQL_IMAGE = "mysql:5.7.22";
  protected static final String DEFAULT_MARIADB_IMAGE = "mariadb:10.3.6";
  protected static final String DEFAULT_POSTGRESQL_IMAGE = "postgres:9.6.12";
  protected static final String DEFAULT_MSSQL_IMAGE = "mcr.microsoft.com/mssql/server:2017-CU12";
  protected static final String DEFAULT_DB2_IMAGE = "ibmcom/db2:11.5.0.0a";
  protected static final String DEFAULT_ORACLE_IMAGE = "registry.camunda.com/camunda-ci-oracle:18";

  protected static final DatabaseImageResolver INSTANCE = new DatabaseImageResolver();

  DatabaseImageResolver() {
  }

  public static String getDatabaseImageForType(String databaseType) {
    switch (databaseType) {
      case "mysql":
        return getInstance().resolveMySQLImageName();
      case "postgresql":
        return getInstance().resolvePostgreSQLImageName();
      case "mariadb":
        return getInstance().resolveMariaDbImageName();
      case "mssql":
        return getInstance().resolveMsSqlImageName();
      case "db2":
        return getInstance().resolveDb2DbImageName();
      case "oracle":
        return getInstance().resolveOracleDbImageName();
      default:
        return null;
    }
  }

  public String resolveMySQLImageName() {
    return TestcontainersConfiguration.getInstance().getProperties().getProperty("mysql.container.image", DEFAULT_MYSQL_IMAGE);
  }

  public String resolveMariaDbImageName() {
    return TestcontainersConfiguration.getInstance().getProperties().getProperty("mariadb.container.image", DEFAULT_MARIADB_IMAGE);
  }

  public String resolvePostgreSQLImageName() {
    return TestcontainersConfiguration.getInstance().getProperties().getProperty("postgresql.container.image", DEFAULT_POSTGRESQL_IMAGE);
  }

  public String resolveOracleDbImageName() {
    return TestcontainersConfiguration.getInstance().getProperties().getProperty("oracle.container.image", DEFAULT_ORACLE_IMAGE);
  }

  public String resolveMsSqlImageName() {
    return TestcontainersConfiguration.getInstance().getProperties().getProperty("mssql.container.image", DEFAULT_MSSQL_IMAGE);
  }

  public String resolveDb2DbImageName() {
    return TestcontainersConfiguration.getInstance().getProperties().getProperty("db2.container.image", DEFAULT_DB2_IMAGE);
  }

  public static DatabaseImageResolver getInstance() {
    return INSTANCE;
  }
}