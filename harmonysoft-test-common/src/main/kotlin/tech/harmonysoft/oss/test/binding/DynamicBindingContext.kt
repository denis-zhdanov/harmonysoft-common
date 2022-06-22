package tech.harmonysoft.oss.test.binding

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.harmonysoft.oss.test.util.TestUtil.fail
import java.util.concurrent.ConcurrentHashMap

@Component
class DynamicBindingContext {

    private val bindings = ConcurrentHashMap<DynamicBindingKey, Any?>()
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getBinding(key: DynamicBindingKey): Any? {
        return if (bindings.contains(key)) {
            bindings[key]
        } else {
            fail("no binding is found for key $key, available bindings: $bindings")
        }
    }

    fun storeBinding(key: DynamicBindingKey, value: Any?) {
        bindings[key] = value
        logger.info("stored dynamic binding: {}={}", key, value)
    }
}