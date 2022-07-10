package com.mcecelja.pocket.enums

enum class EnvironmentEnum (val url: String) {

    LOCAL_NETWORK("http://192.168.218.14:8443/api/"),
    SOCKET("ws://192.168.218.14:8443/chat");
}