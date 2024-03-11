/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.enums.convertes;

import com.mfptps.appdgessddi.enums.ExerciceStatus;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class ExerciceStatusConverter implements AttributeConverter<ExerciceStatus, Character> {

    @Override
    public Character convertToDatabaseColumn(ExerciceStatus attribute) {
        return attribute.getValueStatus();
    }

    @Override
    public ExerciceStatus convertToEntityAttribute(Character dbData) {
        return ExerciceStatus.getByValue(dbData);
    }

}
