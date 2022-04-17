package com.example.androidmusicplayer.ui

//import androidx.annotation.Nullable
//import org.jetbrains.annotations.NotNull
//import xyz.gianlu.librespot.core.Session
//import xyz.gianlu.librespot.player.Player
//import java.io.IOException
//import java.lang.ref.WeakReference
//
//object LibrespotHolder {
//    @Volatile
//    private var session: WeakReference<Session>? = null
//
//    @Volatile
//    private var player: WeakReference<Player>? = null
//    fun set(@NotNull session: Session?) {
//        LibrespotHolder.session = WeakReference(session)
//    }
//
//    fun set(@NotNull player: Player?) {
//        LibrespotHolder.player = WeakReference(player)
//    }
//
//    fun clear() {
//        val s: Session? = getSession()
//        val p: Player? = getPlayer()
//        if (p != null || s != null) {
//            Thread {
//                p?.close()
//                try {
//                    s?.close()
//                } catch (ignored: IOException) {
//                }
//            }.start()
//        }
//        player = null
//        session = null
//    }
//
//    @Nullable
//    fun getSession(): Session? {
//        return session?.get()
//    }
//
//    @Nullable
//    fun getPlayer(): Player? {
//        return player?.get()
//    }
//
//    fun hasSession(): Boolean {
//        return getSession() != null
//    }
//
//    fun hasPlayer(): Boolean {
//        return getPlayer() != null
//    }
//}
//
