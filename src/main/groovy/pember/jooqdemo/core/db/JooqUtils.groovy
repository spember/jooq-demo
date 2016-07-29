package pember.jooqdemo.core.db

import groovy.transform.CompileStatic
import org.jooq.DataType
import org.jooq.impl.SQLDataType
import pember.jooqdemo.core.db.bindings.MapJsonbBinding

/**
 * @author Steve Pember
 */
@CompileStatic
class JooqUtils {
    public static final DataType<Map> MAP_JSONB =
            SQLDataType.VARCHAR.asConvertedDataType(new MapJsonbBinding())
}
