package cn.nagico.week8.model.user

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(list: List<String>): String {
        return list.joinToString(",")
    }

    override fun convertToEntityAttribute(joined: String?): List<String> {
        return if (joined.isNullOrEmpty()) {
            ArrayList()
        } else {
            joined.split(",").toMutableList()
        }
    }
}