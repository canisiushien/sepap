/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.enums;

import com.mfptps.appdgessddi.utils.AppUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
public enum TypeObjectif {
    STRATEGIQUE('R', AppUtil.OBJECTIF_STRATEGIQUE),
    OPERATIONNEL('E', AppUtil.OBJECTIF_OPERATIONNEL);

    protected String label;
    protected Character valueStatus;

    TypeObjectif(char _value, String _label) {
        this.valueStatus = _value;
        this.label = _label;
    }

    public static TypeObjectif getByValue(Character pValue) {
        return Stream
                .of(TypeObjectif.values())
                .filter(val -> val.getValueStatus()
                .equals(pValue)).findAny().orElse(null);
    }

    public static String[] getLabels() {
        return Stream.of(TypeObjectif.values()).map(TypeObjectif::getLabel).collect(Collectors.toList()).toArray(new String[1]);
    }

    public static String getLabelByValue(Character value) {
        return Optional.ofNullable(getByValue((Character) value)).map(TypeObjectif::getLabel).orElse(null);
    }

    public static TypeObjectif getByLabel(String label) {
        return Stream.of(TypeObjectif.values()).filter(val -> val.getLabel().equals(label)).findAny().orElse(null);
    }

    public static Character getValueByLabel(String label) {
        return Optional.ofNullable(getByLabel(label)).map(TypeObjectif::getValueStatus).orElse(null);
    }

    public Map<String, String> getAsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("value", this.toString());
        map.put("label", label);
        return map;
    }
}
