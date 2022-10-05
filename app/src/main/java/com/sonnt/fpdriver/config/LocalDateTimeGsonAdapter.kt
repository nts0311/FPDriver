package com.sonnt.fpdriver.config

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//class LocalDateTimeGsonAdapter: TypeAdapter<LocalDateTime>() {
//    override fun write(out: JsonWriter?, value: LocalDateTime?) {
//        out?.value(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
//    }
//
//    override fun read(`in`: JsonReader?): LocalDateTime {
//        TODO("Not yet implemented")
//    }
//}