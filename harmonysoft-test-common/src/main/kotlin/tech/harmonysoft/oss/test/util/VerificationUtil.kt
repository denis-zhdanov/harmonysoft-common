package tech.harmonysoft.oss.test.util

import org.slf4j.LoggerFactory
import tech.harmonysoft.oss.common.ProcessingResult
import tech.harmonysoft.oss.test.util.TestUtil.fail
import java.util.concurrent.TimeUnit

object VerificationUtil {

    val POLLED_VERIFICATION_CHECK_FREQUENCY_MS = System.getProperty("verification.polled.frequency.ms")?.takeIf {
        it.isNotBlank()
    }?.toLong() ?: 500L

    val POSITIVE_POLLED_VERIFICATION_TTL_SECONDS = System.getProperty("verification.polled.positive.ttl.seconds")?.takeIf {
        it.isNotBlank()
    }?.toLong() ?: 10L

    val NEGATIVE_POLLED_VERIFICATION_TTL_SECONDS = System.getProperty("verification.polled.negative.ttl.seconds")?.takeIf {
        it.isNotBlank()
    }?.toLong() ?: 2L

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Timeout-based utility for verifying that particular condition eventually happens. It's convenient in case
     * of verifying concurrent processing results when we expect to observe particular state within particular
     * timeout
     */
    fun verifyConditionHappens(
        description: String = "<no-description>",
        checkTtlSeconds: Long = POSITIVE_POLLED_VERIFICATION_TTL_SECONDS,
        checkFrequencyMs: Long = POLLED_VERIFICATION_CHECK_FREQUENCY_MS,
        checker: () -> ProcessingResult<Unit, String>
    ) {
        val endTimeMs = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(checkTtlSeconds)
        var error = "<not provided>"
        logger.info("Starting timed verification for the target condition '{}' to happen", description)
        while (System.currentTimeMillis() < endTimeMs) {
            val result = checker()
            if (result.success) {
                logger.info("Verified that '{}' happened", description)
                return
            } else {
                error = result.failureValue
                Thread.sleep(checkFrequencyMs)
            }
        }

        fail("target condition '$description' is not observed within $checkTtlSeconds seconds. Last "
                + "verification failure error: '$error'")
    }

    /**
     * A complement to [verifyConditionHappens], is used in situations when we want to ensure that particular
     * condition doesn't happen within particular timeout
     */
    fun verifyConditionDoesNotHappen(
        description: String = "<no-description>",
        checkTtlSeconds: Long = NEGATIVE_POLLED_VERIFICATION_TTL_SECONDS,
        checkFrequencyMs: Long = POLLED_VERIFICATION_CHECK_FREQUENCY_MS,
        checker: () -> ProcessingResult<Unit, String>
    ) {
        val endTimeMs = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(checkTtlSeconds)
        logger.info("Starting timed verification for the unexpected condition '{}' not to happen", description)
        while (System.currentTimeMillis() < endTimeMs) {
            val result = checker()
            if (result.success) {
                if (System.currentTimeMillis() >= endTimeMs) {
                    break
                }
                Thread.sleep(checkFrequencyMs)
            } else {
                fail("unexpected condition is detected: '${result.failureValue}'")
            }
        }
        logger.info("Verified that condition '{}' didn't happen", description)
    }
}