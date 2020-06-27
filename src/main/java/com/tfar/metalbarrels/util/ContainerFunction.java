package com.tfar.metalbarrels.util;

@FunctionalInterface
public interface ContainerFunction<T, U, V, W, X, R> {

	R apply(T t, U u,V v, W w, X x);

}
