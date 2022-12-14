/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.snmp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.nifi.processor.exception.ProcessException;
import org.snmp4j.security.UsmUser;

import java.util.List;

public class JsonUsmReader implements UsmReader {

    private final String usmUsersJson;

    public JsonUsmReader(String usmUsersJson) {
        this.usmUsersJson = usmUsersJson;
    }

    @Override
    public List<UsmUser> readUsm() {
        try {
            return UsmJsonParser.parse(usmUsersJson);
        } catch (JsonProcessingException e) {
            throw new ProcessException("Could not parse USM user file, please check the processor details for examples.", e);
        }
    }
}
