package com.mfptps.appdgessddi.enums;

import com.mfptps.appdgessddi.utils.AppUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum TypeIndicateurObjectif {
    IMPACT('P', AppUtil.INDICATEUR_IMPACT),
    EFFET('F', AppUtil.INDICATEUR_EFFET);

    protected String label;
    protected Character valueStatus;

    TypeIndicateurObjectif(char _value, String _label) {
        this.valueStatus = _value;
        this.label = _label;
    }

    public static TypeIndicateurObjectif getByValue(Character pValue) {
        return Stream.of(TypeIndicateurObjectif.values()).filter(val -> val.getValueStatus()
                .equals(pValue)).findAny().orElse(null);
    }

    public static String[] getLabels() {
        return Stream.of(TypeIndicateurObjectif.values()).map(TypeIndicateurObjectif::getLabel).collect(Collectors.toList()).toArray(new String[1]);
    }

    public static String getLabelByValue(Character value) {
        return Optional.ofNullable(getByValue((Character) value)).map(TypeIndicateurObjectif::getLabel).orElse(null);
    }

    public static TypeIndicateurObjectif getByLabel(String label) {
        return Stream.of(TypeIndicateurObjectif.values()).filter(val -> val.getLabel().equals(label)).findAny().orElse(null);
    }

    public static Character getValueByLabel(String label) {
        return Optional.ofNullable(getByLabel(label)).map(TypeIndicateurObjectif::getValueStatus).orElse(null);
    }

    public Map<String, String> getAsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("value", this.toString());
        map.put("label", label);
        return map;
    }
}
