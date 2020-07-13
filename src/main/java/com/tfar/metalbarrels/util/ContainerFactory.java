package com.tfar.metalbarrels.util;

@FunctionalInterface
public interface ContainerFactory<T, U, V, W, R> {

	R apply(T t, U u,V v, W w);

}
