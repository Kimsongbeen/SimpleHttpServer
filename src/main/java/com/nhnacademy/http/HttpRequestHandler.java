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

package com.nhnacademy.http;

import com.nhnacademy.http.channel.Executable;
import com.nhnacademy.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public HttpRequestHandler(RequestChannel requestChannel) {
        if(Objects.isNull(requestChannel)){
            throw new IllegalArgumentException("requestChannel is null");
        }
        log.debug("requestChannel init");
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //TODO#14 requestChannel로 부터 httpJob을 할당 받습니다.
                Executable httpJob = requestChannel.getHttpJob();
                if(Objects.isNull(httpJob)){
                    throw new RuntimeException("httpJob is null");
                }
                httpJob.execute();
                log.debug("requestHandler run!");
            } catch (Exception e) {
                // 상위 레벨의 다른 코드 또는 스레드가 이 스레드가 인터럽트 되었음을 인지 할 수 있습니다.
                if(e.getMessage().contains(InterruptedException.class.getName())){
                    Thread.currentThread().interrupt();
                }
                // 종료될 떄 필요한 코드가 있다면 작성 합니다.
                log.debug("RequestHandler error : {}",e.getMessage(),e);
            }
        }
    }
}
