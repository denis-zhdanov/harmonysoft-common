package tech.harmonysoft.oss.common.type

import org.springframework.stereotype.Component

@Component
class DefaultStringTypeManager : TypeManager<String> {

    override val targetType = String::class

    override val targetContext = TypeManagerContext.DEFAULT

    override fun maybeParse(rawValue: String): String {
        return rawValue.trim()
    }
}