package  com.example.my.test.application.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DataSourceHelper {

    public static void putDataSourceInContext(String dataSourceName,
            Object dataSource)
            throws NamingException {

        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        InitialContext ctx = new InitialContext();

        try {
            ctx.lookup(dataSourceName);
            return;
        } catch (NamingException ex) {
            //continue and init context
        }

        StringBuilder subContextBuilder = new StringBuilder();
        for (String part : dataSourceName.split("/")) {
            if (subContextBuilder.length() > 0) {
                subContextBuilder.append("/");
            }
            subContextBuilder.append(part);
            String subContext = subContextBuilder.toString();
            if (!subContext.equals(dataSourceName)) {
                ctx.createSubcontext(subContext);
            } else {
                break;
            }
        }

        ctx.bind(dataSourceName, dataSource);

    }
}
