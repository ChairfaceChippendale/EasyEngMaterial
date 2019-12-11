package com.ujujzk.ee.data.source.dic.local.model

class Key {

    init {
        System.loadLibrary("Key")
    }

    external fun getKey(squareBracketText: String): String
}