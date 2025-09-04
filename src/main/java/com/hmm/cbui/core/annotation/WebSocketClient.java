/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSocketClient {
  String url(); // 메시지 경로 또는 타입

  Mode mode() default Mode.SEND;

  String[] headers() default {};

  enum Mode {
    CONNECT,
    SEND,
    SEND_ASYNC,
    CLOSE
  }
}
