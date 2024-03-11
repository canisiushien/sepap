/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.enums.convertes;

import com.mfptps.appdgessddi.enums.TypeIndicateurObjectif;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class TypeIndicateurObjectifConverter implements AttributeConverter<TypeIndicateurObjectif, Character> {

    @Override
    public Character convertToDatabaseColumn(TypeIndicateurObjectif attribute) {
        return attribute.getValueStatus();
    }

    @Override
    public TypeIndicateurObjectif convertToEntityAttribute(Character dbData) {
        return TypeIndicateurObjectif.getByValue(dbData);
    }

}
