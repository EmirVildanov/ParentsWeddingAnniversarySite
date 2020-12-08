package com.example

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.call
import io.ktor.features.PartialContent
import io.ktor.features.ContentNegotiation
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.gson.gson
import io.ktor.http.cio.websocket.*
import io.ktor.http.content.static
import io.ktor.http.content.resources
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.request.receiveParameters
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

//val playersManager = PlayersManager()
val players = mutableListOf<String?>()

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(PartialContent)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    install(WebSockets)

    routing {
        static("/static") {
            resources("static")
        }

        route("/") {
            println("Somebody calling this")
            println("Players size is: ${players.size}")
            val session = this
            try {
                println("entered try block")
                get {
                    println("entered get block")
                    call.respond(FreeMarkerContent("home.ftl", null))
                }
                post {
                    println("entered post block")
                    if (players.size == 2) {
                        println("Sending error")
                        call.respond(FreeMarkerContent("home.ftl", mapOf("notification" to "Try to wait for 5 seconds and try again")))
//                        session.close()
                    } else {
                        println("Redirecting to congratulations")
                        players.add("Test")
                        println("Players size is(after clicking the button): ${players.size}")
//                        val playerId = playersManager.connectPlayer(session)
                        while (players.size != 2) {
                            delay(1000)
                        }
                        call.respondRedirect("/congratulations", permanent = false)
                    }
                }
            } finally {
                println("entered finally block")
//                session.close()
            }
        }

        get("/congratulations") {
            call.respond(FreeMarkerContent("congratulations.ftl", null))
        }

        get("/info") {
            call.respond(FreeMarkerContent("congratulations.ftl", null))
        }

//        route("/") {
//            get {
//                call.respond(FreeMarkerContent("home.ftl", null))
//            }
//            post {
//                call.respondRedirect("/login", permanent = false)
//            }
//        }

        route("/login") {
            get {
                call.respond(FreeMarkerContent("login.ftl", null))
            }
            post {
                val post = call.receiveParameters()
                val secretPassword = "I am miserable person who broke the Ubuntu"
                val wrongPasswordAnswer = "I am miserable person who broke the Ubuntu"
                if (post["password"] == secretPassword ) {
                    call.respondRedirect("/info", permanent = false)
                } else {
                    call.respond(FreeMarkerContent("login.ftl", mapOf("error" to wrongPasswordAnswer)))
                }
            }
        }

        get("/info") {
            call.respond(FreeMarkerContent("info.ftl", null))
        }
    }
}

private fun handleConnect(session: WebSocketServerSession) {

}
