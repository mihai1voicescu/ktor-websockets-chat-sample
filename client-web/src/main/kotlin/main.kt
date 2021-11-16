import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.*

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            console.log("New message")
            console.log(message.data.toString())
        }
    } catch (e: Exception) {
        console.log("Error while receiving: $e")
    }
}

//suspend fun DefaultClientWebSocketSession.inputMessages() {
//    while (true) {
//        val message = readLine() ?: ""
//        if (message.equals("exit", true)) return
//        try {
//            send(message)
//        } catch (e: Exception) {
//            console.log("Error while sending: " + e.localizedMessage)
//            return
//        }
//    }
//}

fun main() {
    val client = HttpClient(Js) {
        install(WebSockets)
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = "user", password = "user")
                }
            }
        }
    }

    console.log("HI")
    GlobalScope.launch {
        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/chat") {
            val messageOutputRoutine = launch { outputMessages() }
            console.log("launched")

        }
        console.log("wes")

    }
}