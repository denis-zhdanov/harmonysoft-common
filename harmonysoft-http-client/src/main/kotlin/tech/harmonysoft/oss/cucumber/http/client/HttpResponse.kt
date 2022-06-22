package tech.harmonysoft.oss.cucumber.http.client

data class HttpResponse<T>(
    val status: Int,
    val statusText: String,
    val body: T,
    val headers: Map<String, String>
) {

    override fun toString(): String {
        return "$status $statusText"
    }
}