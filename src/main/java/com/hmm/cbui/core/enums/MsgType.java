/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.enums;

// 메시지 타입
public enum MsgType {
  WELCOME("welcome"), // welcome
  ENGINE("engine"), // engine
  EVENT_FLOW("eventflow"), // eventflow
  DF("defaultFallback"); // default fallback

  private final String code;

  MsgType(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return this.code;
  }
}
