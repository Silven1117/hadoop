/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.yarn.server.resourcemanager.webapp.dao;

import com.google.common.collect.Iterables;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.activities.ActivityNode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO object to display request allocation detailed information.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppRequestAllocationInfo {
  private String requestPriority;
  private String allocationRequestId;
  private String allocationState;
  private List<ActivityNodeInfo> allocationAttempt;

  AppRequestAllocationInfo() {
  }

  AppRequestAllocationInfo(List<ActivityNode> activityNodes) {
    this.allocationAttempt = new ArrayList<>();
    ActivityNode lastActivityNode = Iterables.getLast(activityNodes);
    this.requestPriority = lastActivityNode.getRequestPriority();
    this.allocationRequestId = lastActivityNode.getAllocationRequestId();
    this.allocationState = lastActivityNode.getState().name();
    for (ActivityNode attempt : activityNodes) {
      ActivityNodeInfo containerInfo =
          new ActivityNodeInfo(attempt.getName(), attempt.getState(),
              attempt.getDiagnostic(), attempt.getNodeId());
      this.allocationAttempt.add(containerInfo);
    }
  }

  public String getRequestPriority() {
    return requestPriority;
  }

  public String getAllocationRequestId() {
    return allocationRequestId;
  }

  public String getAllocationState() {
    return allocationState;
  }

  public List<ActivityNodeInfo> getAllocationAttempt() {
    return allocationAttempt;
  }
}
