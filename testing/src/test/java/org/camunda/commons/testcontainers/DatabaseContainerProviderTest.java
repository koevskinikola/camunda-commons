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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * This test should not be run on our CI, as it requires a Docker-in-Docker image to runs succesfully.
 */
public class DatabaseContainerProviderTest {

  /**
   * The current Camunda PostgreSQL images are not compatible with Testcontainers. However,
   * the default Testcontainers PostgreSQL image can be used.
   */
  @Test
  public void testPostgresJdbcTestcontainersUrl() {
    // when
    try (Connection connection = DriverManager.getConnection("jdbc:tc:campostgresql:9.6:///testDb")) {
      connection.setAutoCommit(false);
      ResultSet rs = connection.prepareStatement("SELECT version();").executeQuery();
      if (rs.next()) {
        // then
        String version = rs.getString(1);
        assertThat(version).contains("9.6");
      }
    } catch (SQLException throwables) {
      fail("Testcontainers failed to spin up a Docker container: " + throwables.getMessage());
    }
  }

  /**
   * The current Camunda MariaDB images are compatible with Testcontainers (just a bit slow).
   * The username and password need to be explicitly declared.
   */
  @Test
  public void testMariaDBJdbcTestcontainersUrl() {
    // when
    try (Connection connection = DriverManager.getConnection("jdbc:tc:cammariadb:10.0://localhost:3306/process-engine?user=camunda&password=camunda")) {
      connection.setAutoCommit(false);
      ResultSet rs = connection.prepareStatement("SELECT version();").executeQuery();
      if (rs.next()) {
        // then
        String version = rs.getString(1);
        assertThat(version).contains("10.0");
      }
    } catch (SQLException throwables) {
      fail("Testcontainers failed to spin up a Docker container: " + throwables.getMessage());
    }
  }

  /**
   * The current Camunda MariaDB images are compatible with Testcontainers (just a bit slow).
   * The username and password need to be explicitly declared.
   */
  @Test
  public void testMySQLJdbcTestcontainersUrl() {
    // when
    try (Connection connection = DriverManager.getConnection("jdbc:tc:cammysql:5.7://localhost:3306/process-engine?user=camunda&password=camunda")) {
      connection.setAutoCommit(false);
      ResultSet rs = connection.prepareStatement("SELECT version();").executeQuery();
      if (rs.next()) {
        // then
        String version = rs.getString(1);
        assertThat(version).contains("5.7");
      }
    } catch (SQLException throwables) {
      fail("Testcontainers failed to spin up a Docker container: " + throwables.getMessage());
    }
  }

  /**
   * The current Camunda CockroachDB images are compatible with Testcontainers.
   * The username and password are the CRDB defaults.
   */
  @Test
  public void testCockroachDBJdbcTestcontainersUrl() {
    // when
    try (Connection connection = DriverManager.getConnection("jdbc:tc:camcockroachdb:20.1.3://localhost/postgres?user=root&password=")) {
      connection.setAutoCommit(false);
      ResultSet rs = connection.prepareStatement("SELECT version();").executeQuery();
      if (rs.next()) {
        // then
        String version = rs.getString(1);
        assertThat(version).contains("20.1.3");
      }
    } catch (SQLException throwables) {
      fail("Testcontainers failed to spin up a Docker container: " + throwables.getMessage());
    }
  }

  /**
   * The current Camunda SqlServer images are not compatible with Testcontainers.
   * New (publicly available) Docker images can be used, if the TS Isolation is set correctly.
   */
  @Test
  public void testSqlServerJdbcTestcontainersUrl() {
    // when
    try (Connection connection = DriverManager.getConnection("jdbc:tc:camsqlserver:2017-CU12:///testDb")) {
      connection.setAutoCommit(false);
      ResultSet rs = connection.prepareStatement("SELECT @@VERSION").executeQuery();
      if (rs.next()) {
        // then
        String version = rs.getString(1);
        assertThat(version).contains("2017");
      }
    } catch (SQLException throwables) {
      fail("Testcontainers failed to spin up a Docker container: " + throwables.getMessage());
    }
  }

  /**
   * The current Camunda SqlServer images are not compatible with Testcontainers.
   * New (publicly available) Docker images can be used, if the TS Isolation is set correctly.
   */
  @Test
  public void testDb2JdbcTestcontainersUrl() {
    // when
    try (Connection connection = DriverManager.getConnection("jdbc:tc:camdb2:11.1:///engine?user=camunda&password=camunda")) {
      connection.setAutoCommit(false);
      ResultSet rs = connection.prepareStatement("SELECT * FROM SYSIBMADM.ENV_INST_INFO;").executeQuery();
      if (rs.next()) {
        // then
        String version = rs.getString(1);
        assertThat(version).contains("11.1");
      }
    } catch (SQLException throwables) {
      fail("Testcontainers failed to spin up a Docker container: " + throwables.getMessage());
    }
  }

  /**
   * The current Camunda SqlServer images are not compatible with Testcontainers.
   * New (publicly available) Docker images can be used, if the TS Isolation is set correctly.
   */
  @Test
  public void testOracleJdbcTestcontainersUrl() throws SQLException {
    // when
    Connection connection = DriverManager.getConnection("jdbc:tc:camoracle:thin:@localhost:1521:xe?user=camunda&password=camunda");
    connection.setAutoCommit(false);
    ResultSet rs = connection.prepareStatement("SELECT * FROM v$version;").executeQuery();
    if (rs.next()) {
      // then
      String version = rs.getString(1);
      assertThat(version).contains("18");
    }
  }
}