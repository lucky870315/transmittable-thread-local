package com.alibaba.ttl

import com.alibaba.ttl.threadpool.TtlExecutors
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class InheritableTest {
    @Test
    fun inheritable() {
        val threadPool = Executors.newCachedThreadPool()
        val ttl = TransmittableThreadLocal<String>()
        ttl.set("hello")

        assertEquals("hello", threadPool.submit(Callable { ttl.get() }).get())

        threadPool.shutdown()
    }

    @Test
    fun disableInheritable() {
        val threadPool = Executors.newCachedThreadPool(TtlExecutors.getDefaultDisableInheritableThreadFactory())
        val ttl = TransmittableThreadLocal<String>()
        ttl.set("hello")

        assertNull(threadPool.submit(Callable { ttl.get() }).get())

        threadPool.shutdown()
    }
}
