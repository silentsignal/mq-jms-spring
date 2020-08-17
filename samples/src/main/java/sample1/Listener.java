/*
 * Copyright © 2017, 2020 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package sample1;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.Message;
import javax.jms.TextMessage;
import com.ibm.jms.JMSMessage;
import javax.jms.JMSException;
import java.util.concurrent.TimeUnit;

@Component
public class Listener {
  static boolean warned = false;

  @JmsListener(destination = Application.qName)
  public void receiveMessage(JMSMessage msg) throws JMSException, InterruptedException{
    infinityWarning();
    System.out.println(">>>> Received JMSMEssage, wait a bit...");
    TimeUnit.SECONDS.sleep(10);
    Integer len=getMsgLength(msg);
    if (!(msg instanceof TextMessage)) return;
    System.out.println();
    System.out.println("========================================");
    System.out.println("Received message is: " + len.toString());
    System.out.println("========================================");

  }

  private Integer getMsgLength(JMSMessage msg) throws JMSException{
     return new Integer(msg.getBody(byte[].class).length);  
  }

  void infinityWarning() {
    if (!warned) {
      warned = true;
      System.out.println();
      System.out.println("========================================");
      System.out.println("MQ JMS Listener started for queue: " + Application.qName);
      System.out.println("NOTE: This program does not automatically end - it continues to wait");
      System.out.println("      for more messages, so you may need to hit BREAK to end it.");
      System.out.println("========================================");
    }
  }
}
