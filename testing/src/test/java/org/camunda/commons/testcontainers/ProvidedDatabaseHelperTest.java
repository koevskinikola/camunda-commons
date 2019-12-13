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

import org.camunda.commons.testconainers.ProvidedDatabaseHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProvidedDatabaseHelperTest {

  // given
  protected static ProvidedDatabaseHelper databaseHelper = new ProvidedDatabaseHelper("mariadb");

  @BeforeClass
  public static void setUp() {
    // when
    databaseHelper.startDatabase();
  }

  @Test
  public void testDbSetup() {
    // then
    assertThat(databaseHelper.getDbContainer()).isNotNull();
    assertThat(databaseHelper.getJdbcUrl()).contains("mariadb");
  }

  @AfterClass
  public static void tearDown() {
    databaseHelper.stopDatabase();
  }
}