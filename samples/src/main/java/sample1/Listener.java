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
import javax.jms.ObjectMessage;
import javax.jms.JMSException;

@Component
public class Listener {
  static boolean warned = false;

  @JmsListener(destination = Application.qName)
  public void receiveMessage(ObjectMessage msg){
    infinityWarning();
    try{
        System.out.println();
        System.out.println("========================================");
        System.out.println("Received message is: "+((Sample)msg.getObject()).foo);
        System.out.println("========================================");
    }catch(JMSException jmse){
        System.out.println(jmse.toString());    
    }

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
