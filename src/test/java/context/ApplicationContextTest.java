/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package context;

import com.nhnacademy.http.context.Context;
import com.nhnacademy.http.context.ContextHolder;
import com.nhnacademy.http.context.exception.ObjectNotFoundException;
import com.nhnacademy.http.service.InfoHttpService;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationContextTest {
    @BeforeEach
    void setUp(){
        Context context = ContextHolder.getApplicationContext();
        //TODO#101 context에 indexHttpService를 등록 합니다.
        //name : indexHttpService, object: new IndexHttpService()

    }

    @Test
    @Order(1)
    void setAttribute1() {
        Context context = ContextHolder.getApplicationContext();
        context.setAttribute("name",new Object());

        //TODO#102 context에 "name"에 해당되는 객체가 존재하는지 검증 합니다.

    }

    @Test
    @Order(2)
    @DisplayName("setAttribute object is null")
    void setAttribute2() {
        Context context = ContextHolder.getApplicationContext();

        //TODO#103 context에 다음과 같이 null Object를 동록시 IllegalArgumentException이 발생하는지 검증 합니다.
        // - context.setAttribute("something",null);

    }

    @Test
    @Order(3)
    void removeAttribute1() {
        Context context = ContextHolder.getApplicationContext();
        String name = "indexHttpService";

        context.removeAttribute(name);

        //TODO#104 name에 해당되는 객체를 remove 했습니다. name에 해당되는 객체를 다음과 같이 context로 부터 획득하려고 할 때 ObjectNotFoundException이 발생하는지 검증 합니다.
        // - context.getAttribute(name);

    }

    @Test
    @Order(4)
    @DisplayName("removeAttribute name is {null or empty}")
    void removeAttribute2() {
        //TODO#105 context.removeAttribute(""); or context.removeAttribute(null); 실행할 때  IllegalArgumentException 발생하는지 검증 합니다.

    }

    @Test
    @Order(5)
    void getAttribute1() {
        Context context = ContextHolder.getApplicationContext();
        InfoHttpService infoHttpService = new InfoHttpService();
        context.setAttribute("infoHttpService", infoHttpService);
        Assertions.assertEquals(infoHttpService, context.getAttribute("infoHttpService") );
    }

    @Test
    @Order(6)
    @DisplayName("getAttribute, object not found exception")
    void getAttribute2() {
        Context context = ContextHolder.getApplicationContext();
        Assertions.assertThrows(ObjectNotFoundException.class, ()->{
            context.getAttribute("something");
        });
    }

    @Test
    @Order(7)
    @DisplayName("getAttribute name is null or empty")
    void getAttribute3(){
        Context context = ContextHolder.getApplicationContext();
        //TODO#106 getAttribute를 다음과 같이 호출할 때 IllegalArgumentException Exception이 발생하는지 검증하세요
        // - context.getAttribute(null);
        // - context.getAttribute("");

    }

    @Test
    @Order(8)
    @DisplayName("shared ContextHolder")
    void sharedContextHolder() throws InterruptedException {
        Thread thread1 = new Thread(()->{
            //TODO#107 thread내에서 context에 counter 값을 10으로 설정 합니다.
            Context context = null;
        });

        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(()->{
            //TODO#108 thread내에서 context에 counter = counter+1 후  context에 재 할당 합니다.
            Context context = null;
        });

        thread2.start();
        thread2.join();

        Context context = ContextHolder.getApplicationContext();
        Assertions.assertEquals(11, context.getAttribute("counter"));
    }

}