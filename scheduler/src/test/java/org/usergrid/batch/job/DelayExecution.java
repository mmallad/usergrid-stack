/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.batch.job;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.usergrid.batch.Job;
import org.usergrid.batch.JobExecution;

/**
 * A simple job that does nothing but increment an atomic counter
 * 
 * @author tnine
 * 
 */
@Component("delayExecution")
@Ignore("Not a test")
public class DelayExecution implements Job {

  private static final Logger logger = LoggerFactory.getLogger(DelayExecution.class);
  
  private CountDownLatch latch = null;
  private long timeout;
  private int delayCount;
  
  /**
   * 
   */
  public DelayExecution() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.usergrid.batch.Job#execute(org.usergrid.batch.JobExecution)
   */
  @Override
  public void execute(JobExecution execution) throws Exception {
    
    logger.info("Running delay execution");
    
    if(latch.getCount() > 1){
      execution.delay(timeout);
    }
    
    latch.countDown();

    
    
  
  }
  
  public void setLatch(int calls){
    latch = new CountDownLatch(calls);
  }

  public boolean waitForCount(long timeout, TimeUnit unit) throws InterruptedException {
    return latch.await(timeout, unit);
  }

  

  /**
   * @return the timeout
   */
  public long getTimeout() {
    return timeout;
  }

  /**
   * @param timeout
   *          the timeout to set
   */
  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }

  /**
   * @return the delayCount
   */
  public int getDelayCount() {
    return delayCount;
  }

  /**
   * @param delayCount the delayCount to set
   */
  public void setDelayCount(int delayCount) {
    this.delayCount = delayCount;
  }
  
}
