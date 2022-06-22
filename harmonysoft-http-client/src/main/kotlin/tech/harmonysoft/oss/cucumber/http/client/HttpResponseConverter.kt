package tech.harmonysoft.oss.cucumber.http.client

import java.nio.charset.Charset

fun interface HttpResponseConverter<T> {

    fun convert(rawResponse: ByteArray, charset: Charset): T

    companion object {

        val BYTE_ARRAY = HttpResponseConverter { rawResponse, _ ->
            rawResponse
        }

        val STRING = HttpResponseConverter { rawResponse, charset ->
            if (rawResponse.isEmpty()) {
                ""
            } else {
                String(rawResponse, charset)
            }
        }

    }
}