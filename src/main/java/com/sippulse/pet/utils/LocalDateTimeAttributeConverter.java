package com.sippulse.pet.utils;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe converter criada para que um LocalDateTime da entidade possa ser tratado como Timestamp pelo banco.
 * No JPA 2.1, não há suporte para os tipos de data novos do Java 8. Sem esse converter, filtros por data não funcionam.
 * @author schmitt
 *
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
     
	/**
	 * Converte de LocalDateTime para Timestamp
	 */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
    }
 
    /**
     * Converte de Timestamp para LocalDateTime
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
    }
}