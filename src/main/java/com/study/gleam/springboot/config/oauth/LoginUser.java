package com.study.gleam.springboot.config.oauth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 이 어노테이션이 생성될 수 있는 위치 지정 (PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용 가능)
@Retention(RetentionPolicy.RUNTIME) // 이 파일을 어노테이션 클래스로 지정 (LoginUser 이름의 어노테이션이 생성되었다고 보면 됨)
public @interface LoginUser {
}
