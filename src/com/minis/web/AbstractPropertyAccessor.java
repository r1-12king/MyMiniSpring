package com.minis.web;

import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;

/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-14 16:35
 **/
public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport {

    PropertyValues pvs;

    public AbstractPropertyAccessor() {
        super();

    }


    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    public abstract void setPropertyValue(PropertyValue pv);

}
