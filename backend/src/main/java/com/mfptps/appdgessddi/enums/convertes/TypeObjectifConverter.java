/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.enums.convertes;

import com.mfptps.appdgessddi.enums.TypeObjectif;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class TypeObjectifConverter implements AttributeConverter<TypeObjectif, Character> {

    @Override
    public Character convertToDatabaseColumn(TypeObjectif attribute) {
        return attribute.getValueStatus();
    }

    @Override
    public TypeObjectif convertToEntityAttribute(Character dbData) {
        return TypeObjectif.getByValue(dbData);
    }

}
