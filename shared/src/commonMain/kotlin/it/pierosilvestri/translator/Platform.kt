package it.pierosilvestri.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform