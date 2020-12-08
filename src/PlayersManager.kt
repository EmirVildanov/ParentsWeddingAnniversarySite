package com.example

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class PlayersManager {
    private val players: MutableList<Route?> = MutableList(2) { null }
    val isFull: Boolean
        get() = !players.contains(null)

    fun connectPlayer(session: Route): Int {
        if (isFull) {
            throw LobbyIsFullException()
        }
        val playerId = players.indexOf(null)
        players[playerId] = session
        return playerId
    }

    fun forgetPlayer(session: Route): Int {
        val playerId = players.indexOf(session)
        if (playerId == -1) {
            throw PlayerNotInLobbyException()
        }
        players[playerId] = null
        return playerId
    }

    class LobbyIsFullException : IllegalStateException()
    class PlayerNotInLobbyException : IllegalArgumentException()
}