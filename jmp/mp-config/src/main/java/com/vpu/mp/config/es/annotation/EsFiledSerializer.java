package com.vpu.mp.config.es.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;


public class EsFiledSerializer extends JacksonAnnotationIntrospector implements Versioned {
    @Override
    public PropertyName findNameForSerialization(Annotated a){
        boolean useDefault = false;
        EsFiled jg = _findAnnotation(a, EsFiled.class);
        if (jg != null) {
            String s = jg.name();
            if (!s.isEmpty()) {
                return PropertyName.construct(s);
            }
        }
        return null;
    }
    @Override
    public boolean hasIgnoreMarker(AnnotatedMember m) {
        return _isIgnorable(m);
    }

}
