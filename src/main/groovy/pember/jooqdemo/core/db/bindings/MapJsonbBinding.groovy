package pember.jooqdemo.core.db.bindings

import groovy.transform.CompileStatic
import org.jooq.Binding
import org.jooq.BindingGetResultSetContext
import org.jooq.BindingGetSQLInputContext
import org.jooq.BindingGetStatementContext
import org.jooq.BindingRegisterContext
import org.jooq.BindingSQLContext
import org.jooq.BindingSetSQLOutputContext
import org.jooq.BindingSetStatementContext
import org.jooq.impl.DSL
import pember.jooqdemo.core.db.converters.MapJsonbConverter

import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types

/**
 * @author Steve Pember
 */
@CompileStatic
class MapJsonbBinding implements Binding<String, Map> {

    @Override
    public MapJsonbConverter converter() {
        new MapJsonbConverter()
    }

    @Override
    public void sql(BindingSQLContext<Map> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::jsonb");
    }

    @Override
    public void register(BindingRegisterContext<Map> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR)
    }

    @Override
    public void set(BindingSetStatementContext<Map> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null))
    }

    @Override
    public void get(BindingGetResultSetContext<Map> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()))
    }

    @Override
    public void get(BindingGetStatementContext<Map> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()))
    }

    @Override
    public void set(BindingSetSQLOutputContext<Map> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException()
    }

    @Override
    public void get(BindingGetSQLInputContext<Map> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException()
    }
}