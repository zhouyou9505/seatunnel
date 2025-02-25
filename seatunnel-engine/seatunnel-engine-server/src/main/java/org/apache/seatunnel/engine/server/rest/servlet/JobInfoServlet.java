/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.engine.server.rest.servlet;

import org.apache.seatunnel.engine.server.rest.service.JobInfoService;

import com.hazelcast.spi.impl.NodeEngineImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JobInfoServlet extends BaseServlet {

    private final JobInfoService jobInfoService;

    public JobInfoServlet(NodeEngineImpl nodeEngine) {
        super(nodeEngine);
        this.jobInfoService = new JobInfoService(nodeEngine);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String jobIdStr = req.getPathInfo();

        if (jobIdStr != null && jobIdStr.length() > 1) {
            jobIdStr = jobIdStr.substring(1);
        } else {
            throw new IllegalArgumentException("The jobId must not be empty.");
        }
        Long jobId = Long.valueOf(jobIdStr);

        writeJson(resp, jobInfoService.getJobInfoJson(jobId));
    }
}
