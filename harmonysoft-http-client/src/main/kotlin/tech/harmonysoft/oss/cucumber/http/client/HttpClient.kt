package tech.harmonysoft.oss.cucumber.http.client

interface HttpClient {

    fun get(url: String, headers: Map<String, String> = emptyMap()): HttpResponse<String> {
        return get(url, headers, HttpResponseConverter.STRING)
    }

    fun <T> get(url: String, headers: Map<String, String>, converter: HttpResponseConverter<T>): HttpResponse<T>


}