package pember.jooqdemo.core.db.converters

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.jooq.Converter

/**
 * For converting the text of a JSONB column from a string into a map
 *
 * @author Steve Pember
 */
@CompileStatic
class MapJsonbConverter implements Converter<String, Map> {
    @Override
    Map from(String t) {
        if(!t) {
            return null
        }
        // or, could use Jackson
        (new JsonSlurper()).parseText(t) as Map
    }

    @Override
    String to(Map u) {
        return u == null ? null : (new JsonBuilder(u)).toString()
    }

    @Override
    Class<String> fromType() {
        String.class;
    }

    @Override
    Class<Map> toType() {
        Map.class;
    }
}