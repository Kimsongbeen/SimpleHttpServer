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

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
/* TODO#6 Java에서 Thread는 implements Runnable or extends Thread를 이용해서 Thread를 만들 수 있습니다.
 *  implements Runnable을 사용하여 구현 합니다.
 */
public class HttpRequestHandler implements  Runnable{
    private final Socket client;

    private final static String CRLF="\r\n";

    public HttpRequestHandler(Socket client) {
        //TODO#7 생성자를 초기화 합니다., cleint null or socket close 되었다면 적절히 Exception을 발생시킵니다.
        if(Objects.isNull(client)){
            throw new IllegalArgumentException("client is null");
        }
        this.client = client;
    }


    @Override
    public void run() {
        //TODO#8 exercise-simple-http-server-step1을 참고 하여 구현 합니다.
        StringBuilder requestBuilder = new StringBuilder();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ){
            while(true){
                String line = bufferedReader.readLine();

                requestBuilder.append(line);

                log.debug("line: {}", line);

                if(Objects.isNull(line) || line.isEmpty()){
                    break;
                }
            }

            StringBuilder responseBody = new StringBuilder();
            responseBody.append("<html>");
            responseBody.append("<body>");
            responseBody.append("<h1>hello java! -step2-</h1>");
            responseBody.append("</body>");
            responseBody.append("</html>");

            StringBuilder responseHeader = getStringBuilder(responseBody);

            bufferedWriter.write(responseHeader.toString());
            bufferedWriter.write(responseBody.toString());
            bufferedWriter.flush();

            log.debug("header: {}", responseHeader);
            log.debug("body: {}", responseBody);
        }catch(IOException e){
            log.error("socket error: {}", e);
        }
    }

    private static StringBuilder getStringBuilder(StringBuilder responseBody) {
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(String.format("HTTP/1.1 200 OK%s", CRLF));
        responseHeader.append(String.format("Server: HTTP server/0.1%s", CRLF));
        responseHeader.append(String.format("Content-Type: text/html; charset=%s%s", "utf-8", CRLF));
        responseHeader.append(String.format("Connection: closed%s", CRLF));
        responseHeader.append(String.format("Content-Length: %d%s%s", responseBody.toString().getBytes().length, CRLF, CRLF));
        return responseHeader;
    }
}
