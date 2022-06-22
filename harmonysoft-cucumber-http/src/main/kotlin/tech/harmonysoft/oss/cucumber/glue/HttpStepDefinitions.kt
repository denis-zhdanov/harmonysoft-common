package tech.harmonysoft.oss.cucumber.glue

import io.cucumber.java.After
import io.cucumber.java.en.When
import tech.harmonysoft.oss.cucumber.http.client.HttpClient
import tech.harmonysoft.oss.cucumber.http.client.HttpResponse
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class HttpStepDefinitions {

    @Inject private lateinit var httpClient: HttpClient

    private val responses = ConcurrentHashMap<HttpMethod, List<ResponseRecord>>()

    @After
    fun tearDown() {
        responses.clear()
    }

    @When("^([^\\s]+) request to ([^\\s]+) with json body is made:$")
    fun makeRequestWithJsonBody(rawMethod: String, path: String, body: String) {

    }

    fun getFullUrl(path: String): String {
        TODO("implement")
    }

    data class ResponseRecord(val url: String, val response: HttpResponse<ByteArray>)
}