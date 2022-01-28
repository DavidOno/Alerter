package de.boettcher.alerter.core

import de.boettcher.alerter.core.register.Privileges
import javax.persistence.AttributeConverter
import javax.persistence.Converter

//@Converter(autoApply = true)
//class PrivilegesConverter: AttributeConverter<Privileges, String>{
//    override fun convertToDatabaseColumn(p0: Privileges?): String? {
//        if(p0 == null){
//            return null;
//        }
//        return
//    }
//
//    override fun convertToEntityAttribute(p0: String?): Privileges {
//        TODO("Not yet implemented")
//    }
//
//}