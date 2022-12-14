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
package org.apache.nifi.processors.standard;

import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processors.standard.util.JmsFactory;
import org.apache.nifi.processors.standard.util.JmsProperties;
import org.apache.nifi.processors.standard.util.WrappedMessageProducer;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.jupiter.api.Test;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.StreamMessage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("deprecation")
public class TestGetJMSQueue {

    @Test
    public void testSendTextToQueue() throws Exception {
        PutJMS putJms = new PutJMS();
        TestRunner putRunner = TestRunners.newTestRunner(putJms);
        putRunner.setProperty(JmsProperties.JMS_PROVIDER, JmsProperties.ACTIVEMQ_PROVIDER);
        putRunner.setProperty(JmsProperties.URL, "vm://localhost?broker.persistent=false");
        putRunner.setProperty(JmsProperties.DESTINATION_TYPE, JmsProperties.DESTINATION_TYPE_QUEUE);
        putRunner.setProperty(JmsProperties.DESTINATION_NAME, "queue.testing");
        putRunner.setProperty(JmsProperties.ACKNOWLEDGEMENT_MODE, JmsProperties.ACK_MODE_AUTO);

        WrappedMessageProducer wrappedProducer = JmsFactory.createMessageProducer(putRunner.getProcessContext(), true);
        final Session jmsSession = wrappedProducer.getSession();
        final MessageProducer producer = wrappedProducer.getProducer();
        final Message message = jmsSession.createTextMessage("Hello World");

        producer.send(message);
        jmsSession.commit();

        GetJMSQueue getJmsQueue = new GetJMSQueue();
        TestRunner runner = TestRunners.newTestRunner(getJmsQueue);
        runner.setProperty(JmsProperties.JMS_PROVIDER, JmsProperties.ACTIVEMQ_PROVIDER);
        runner.setProperty(JmsProperties.URL, "vm://localhost?broker.persistent=false");
        runner.setProperty(JmsProperties.DESTINATION_NAME, "queue.testing");
        runner.setProperty(JmsProperties.ACKNOWLEDGEMENT_MODE, JmsProperties.ACK_MODE_AUTO);

        runner.run();

        List<MockFlowFile> flowFiles = runner
                .getFlowFilesForRelationship(new Relationship.Builder().name("success").build());

        assertEquals(1, flowFiles.size());
        MockFlowFile successFlowFile = flowFiles.get(0);
        successFlowFile.assertContentEquals("Hello World");
        successFlowFile.assertAttributeEquals("jms.JMSDestination", "queue.testing");
        producer.close();
        jmsSession.close();
    }

    @Test
    public void testSendBytesToQueue() throws Exception {
        PutJMS putJms = new PutJMS();
        TestRunner putRunner = TestRunners.newTestRunner(putJms);
        putRunner.setProperty(JmsProperties.JMS_PROVIDER, JmsProperties.ACTIVEMQ_PROVIDER);
        putRunner.setProperty(JmsProperties.URL, "vm://localhost?broker.persistent=false");
        putRunner.setProperty(JmsProperties.DESTINATION_TYPE, JmsProperties.DESTINATION_TYPE_QUEUE);
        putRunner.setProperty(JmsProperties.DESTINATION_NAME, "queue.testing");
        putRunner.setProperty(JmsProperties.ACKNOWLEDGEMENT_MODE, JmsProperties.ACK_MODE_AUTO);
        WrappedMessageProducer wrappedProducer = JmsFactory.createMessageProducer(putRunner.getProcessContext(), true);
        final Session jmsSession = wrappedProducer.getSession();
        final MessageProducer producer = wrappedProducer.getProducer();
        final BytesMessage message = jmsSession.createBytesMessage();
        message.writeBytes("Hello Bytes".getBytes());

        producer.send(message);
        jmsSession.commit();

        GetJMSQueue getJmsQueue = new GetJMSQueue();
        TestRunner runner = TestRunners.newTestRunner(getJmsQueue);
        runner.setProperty(JmsProperties.JMS_PROVIDER, JmsProperties.ACTIVEMQ_PROVIDER);
        runner.setProperty(JmsProperties.URL, "vm://localhost?broker.persistent=false");
        runner.setProperty(JmsProperties.DESTINATION_NAME, "queue.testing");
        runner.setProperty(JmsProperties.ACKNOWLEDGEMENT_MODE, JmsProperties.ACK_MODE_AUTO);

        runner.run();

        List<MockFlowFile> flowFiles = runner
                .getFlowFilesForRelationship(new Relationship.Builder().name("success").build());

        assertEquals(1, flowFiles.size());
        MockFlowFile successFlowFile = flowFiles.get(0);
        successFlowFile.assertContentEquals("Hello Bytes");
        successFlowFile.assertAttributeEquals("jms.JMSDestination", "queue.testing");
        producer.close();
        jmsSession.close();
    }

    @Test
    public void testSendStreamToQueue() throws Exception {
        PutJMS putJms = new PutJMS();
        TestRunner putRunner = TestRunners.newTestRunner(putJms);
        putRunner.setProperty(JmsProperties.JMS_PROVIDER, JmsProperties.ACTIVEMQ_PROVIDER);
        putRunner.setProperty(JmsProperties.URL, "vm://localhost?broker.persistent=false");
        putRunner.setProperty(JmsProperties.DESTINATION_TYPE, JmsProperties.DESTINATION_TYPE_QUEUE);
        putRunner.setProperty(JmsProperties.DESTINATION_NAME, "queue.testing");
        putRunner.setProperty(JmsProperties.ACKNOWLEDGEMENT_MODE, JmsProperties.ACK_MODE_AUTO);
        WrappedMessageProducer wrappedProducer = JmsFactory.createMessageProducer(putRunner.getProcessContext(), true);
        final Session jmsSession = wrappedProducer.getSession();
        final MessageProducer producer = wrappedProducer.getProducer();

        final StreamMessage message = jmsSession.createStreamMessage();
        message.writeBytes("Hello Stream".getBytes());

        producer.send(message);
        jmsSession.commit();

        GetJMSQueue getJmsQueue = new GetJMSQueue();
        TestRunner runner = TestRunners.newTestRunner(getJmsQueue);
        runner.setProperty(JmsProperties.JMS_PROVIDER, JmsProperties.ACTIVEMQ_PROVIDER);
        runner.setProperty(JmsProperties.URL, "vm://localhost?broker.persistent=false");
        runner.setProperty(JmsProperties.DESTINATION_NAME, "queue.testing");
        runner.setProperty(JmsProperties.ACKNOWLEDGEMENT_MODE, JmsProperties.ACK_MODE_AUTO);

        runner.run();

        List<MockFlowFile> flowFiles = runner
                .getFlowFilesForRelationship(new Relationship.Builder().name("success").build());

        assertEquals(1, flowFiles.size());
        MockFlowFile successFlowFile = flowFiles.get(0);
        successFlowFile.assertContentEquals("Hello Stream");
        successFlowFile.assertAttributeEquals("jms.JMSDestination", "queue.testing");

        producer.close();
        jmsSession.close();
    }
}
