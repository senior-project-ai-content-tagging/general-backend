package me.ponlawat.infrastructure.auth;

import javax.interceptor.InterceptorBinding;
import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE})
@NameBinding
public @interface ApiKeyRequired {
}
