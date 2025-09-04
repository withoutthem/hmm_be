/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // 메소드와 클래스 레벨에 사용 가능
@Retention(RetentionPolicy.RUNTIME) // 런타임에도 어노테이션 정보 유지
public @interface AopIgnore {}
