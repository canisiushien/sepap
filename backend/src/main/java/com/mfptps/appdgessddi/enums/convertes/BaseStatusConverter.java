/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.enums.convertes;

import com.mfptps.appdgessddi.enums.BaseStatus;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class BaseStatusConverter implements AttributeConverter<BaseStatus, Character> {

    @Override
    public Character convertToDatabaseColumn(BaseStatus attribute) {
        return attribute.getValueStatus();
    }

    @Override
    public BaseStatus convertToEntityAttribute(Character dbData) {
        return BaseStatus.getByValue(dbData);
    }

}
