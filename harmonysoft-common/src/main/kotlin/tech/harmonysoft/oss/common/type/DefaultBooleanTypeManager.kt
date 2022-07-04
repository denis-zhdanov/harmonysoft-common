package tech.harmonysoft.oss.common.type

import org.springframework.stereotype.Component

@Component
class DefaultBooleanTypeManager : TypeManager<Boolean> {

    override val targetType = Boolean::class

    override val targetContext = TypeManagerContext.DEFAULT

    override fun maybeParse(rawValue: String): Boolean? {
        return rawValue.trim().takeIf(String::isNotEmpty)?.toBoolean()
    }
}