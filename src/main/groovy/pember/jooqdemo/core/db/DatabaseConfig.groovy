package pember.jooqdemo.core.db

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString
@CompileStatic
class DatabaseConfig {
    String url
    String username
    String password
}