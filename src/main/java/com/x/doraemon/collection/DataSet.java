package com.x.doraemon.collection;

import com.x.doraemon.bean.New;
import com.x.doraemon.util.ConvertHelper;
import com.x.doraemon.util.StringHelper;

import java.util.Map;
import java.util.Optional;

/**
 * @Desc TODO
 * @Date 2020/10/21 19:10
 * @Author AD
 */
public class DataSet {
    
    private Map<String, Object> datas = New.map();
    
    public DataSet() {
    }
    
    public void add(Object name, Object value) {
        if (name != null) {
            this.datas.put(StringHelper.toUpperCase(name), value);
        }
    }
    
    public void addAll(DataSet dataSet) {
        if (!isEmpty(dataSet)) {
            this.datas.putAll(dataSet.datas);
        }
    }
    
    public void addAll(Map<String, Object> map) {
        if (map != null && map.size() != 0) {
            map.entrySet().stream().forEach(next -> datas.put(StringHelper.toUpperCase(next.getKey()), next.getValue()));
        }
    }
    
    public Map<String, Object> getMap() {
        return this.datas;
    }
    
    public int size() {
        return this.datas.size();
    }
    
    public void clear() {
        this.datas.clear();
    }
    
    public Object remove(Object key) {
        return key == null ? null : datas.remove(StringHelper.toUpperCase(key));
    }
    
    public boolean containsKey(Object key) {
        return key != null && datas.containsKey(StringHelper.toUpperCase(key));
    }
    
    public <T> T get(Object key) {
        return key == null ? null : (T) datas.get(StringHelper.toUpperCase(key));
    }
    
    public String getString(Object key) {
        return getString(key, (String) null);
    }
    
    public String getString(Object key, String defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        return Optional.ofNullable(value).orElseGet(() -> StringHelper.isNull(defaultValue) ? "" : defaultValue).toString();
    }
    
    public short getShort(Object key) {
        return this.getShort(key, (short) 0);
    }
    
    public short getShort(Object key, short defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Short.TYPE)) {
            try {
                return Short.valueOf(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (short) value;
        }
    }
    
    public int getInt(Object key) {
        return this.getInt(key, 0);
    }
    
    public int getInt(Object key, int defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Integer.TYPE)) {
            try {
                return Integer.parseInt(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (int) value;
        }
    }
    
    public long getLong(Object key) {
        return this.getLong(key, 0L);
    }
    
    public long getLong(Object key, long defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Long.TYPE)) {
            try {
                return Long.parseLong(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (long) value;
        }
    }
    
    public double getDouble(Object key) {
        return this.getDouble(key, 0.0D);
    }
    
    public double getDouble(Object key, double defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Double.TYPE)) {
            try {
                return Double.parseDouble(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (double) value;
        }
    }
    
    public float getFloat(Object key) {
        return this.getFloat(key, 0.0F);
    }
    
    public float getFloat(Object key, float defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Float.TYPE)) {
            try {
                return Float.parseFloat(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (float) value;
        }
    }
    
    public boolean getBoolean(Object key) {
        return this.getBoolean(key, false);
    }
    
    public boolean getBoolean(Object key, boolean defaultValue) {
        Object value = datas.get(StringHelper.toUpperCase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Boolean.TYPE)) {
            String t = StringHelper.toUpperCase(value);
            return ConvertHelper.toBoolean(t);
        } else {
            return (boolean) value;
        }
    }
    
    @Override
    public String toString() {
        return datas.toString();
    }
    
    // ---------------------- 辅助方法 ----------------------
    
    private boolean isEmpty(DataSet dataSet) {
        return dataSet == null || dataSet.datas.size() == 0;
    }
    
}
