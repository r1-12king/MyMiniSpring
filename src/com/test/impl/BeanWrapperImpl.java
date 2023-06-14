package com.test.impl;

import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;
import com.minis.web.AbstractPropertyAccessor;
import com.minis.web.PropertyEditor;
import com.minis.web.PropertyEditorRegistrySupport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author admin
 */
public class BeanWrapperImpl extends AbstractPropertyAccessor {
    Object wrappedObject; //目标对象
    Class<?> clz;

    public BeanWrapperImpl(Object object) {
        super();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public Object getBeanInstance() {
        return wrappedObject;
    }

    public void setBeanInstance(Object object) {
        this.wrappedObject = object;
    }


    //绑定具体某个参数
    @Override
    public void setPropertyValue(PropertyValue pv) {
        //拿到参数处理器
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        //找到对该参数类型的editor
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        //设置参数值
        if (pe == null) {
            // 没有自定义editor  设置默认editor
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        if (pe != null) {
            pe.setAsText((String) pv.getValue());
            propertyHandler.setValue(pe.getValue());
        }
        else {
            propertyHandler.setValue(pv.getValue());
        }
    }

    /**
     * 一个内部类，用于处理参数，通过getter()和setter()操作属性
     */
    class BeanPropertyHandler {
        Method writeMethod = null;
        Method readMethod = null;
        Class<?> propertyClz = null;

        public BeanPropertyHandler(String propertyName) {
            try {
                //获取参数对应的属性及类型
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                //获取设置属性的方法，按照约定为setXxxx（）
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                //获取读属性的方法，按照约定为getXxxx（）
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        //调用getter读属性值
        public Object getValue() {
            Object result = null;
            writeMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        //调用setter设置属性值
        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}