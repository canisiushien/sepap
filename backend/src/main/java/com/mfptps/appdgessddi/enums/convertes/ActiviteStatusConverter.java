/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.enums.convertes;

import com.mfptps.appdgessddi.enums.ActiviteStatus;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class ActiviteStatusConverter implements AttributeConverter<ActiviteStatus, Character> {

    @Override
    public Character convertToDatabaseColumn(ActiviteStatus attribute) {
        return attribute.getValueStatus();
    }

    @Override
    public ActiviteStatus convertToEntityAttribute(Character dbData) {
        return ActiviteStatus.getByValue(dbData);
    }

}
