package com.example.my.test.application;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.example.my.test.application.hibernate.HibernateUtil;
import com.example.my.test.application.util.DataSourceHelper;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;




public class MainModule {
	private static final String DATA_SOURCE_NAME =  "java:/comp/env/jdbc/test-data";

	public static void main(String[] args) throws Exception  {
		CommandLine cl = parseCLArgs(args);

		initDb(
		        cl.getOptionValue("dbusername"),
		        cl.getOptionValue("dbpassword"),
		        cl.getOptionValue("dbhost"),
		        Integer.parseInt(cl.getOptionValue("dbport")),
		        cl.getOptionValue("dbname"));

		ScraperManager.fetch(cl.getOptionValue("config"));
		HibernateUtil.shutdown();  

	}
	static void initDb (String userName, String password, String host, int port, String dbName) throws Exception{
		MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
		
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setServerName(host);
        dataSource.setPort(port);
        dataSource.setDatabaseName(dbName);

		DataSourceHelper.putDataSourceInContext(DATA_SOURCE_NAME, dataSource);
		
	}
	
    @SuppressWarnings("static-access")
	private static Options prepareOptions(){
        

        Option dbHostOpt = OptionBuilder.hasArg().isRequired().
                withDescription("Database server host").create("dbHost");
        Option dbPortOpt = OptionBuilder.hasArg().isRequired().
                withDescription("Database port. Must be whole number").create("dbPort");
        Option dbUsernameOpt = OptionBuilder.hasArg().isRequired().
                withDescription("Database username").create("dbUsername");
        Option dbPaswordOpt = OptionBuilder.hasArg().isRequired().
                withDescription("Database password").create("dbPassword");
        Option dbNameOpt = OptionBuilder.hasArg().isRequired().
                withDescription("Database name").create("dbName");
        Option configFilePath = OptionBuilder.hasArg().withDescription("Config file location").create("config");


        Options options = new Options();
        options.addOption(dbHostOpt);
        options.addOption(dbPortOpt);
        options.addOption(dbUsernameOpt);
        options.addOption(dbPaswordOpt);
        options.addOption(dbNameOpt);
        options.addOption(configFilePath);

        return options;
    }



    private static CommandLine parseCLArgs(String[] args) throws FileNotFoundException, IOException{
        CommandLineParser parser = new GnuParser();
        Options options = prepareOptions();
        try {
            // parse the command line arguments
            CommandLine cl = parser.parse( options, args );
            cl = parser.parse( options, args );
            String dbportStr = cl.getOptionValue("dbport");
            try{
                Integer.parseInt(dbportStr);
            }catch(NumberFormatException ex){
                throw new ParseException("dbport must be whole number, but was " +
                        dbportStr);
            }
            return cl;
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.out.println( "Can not parse arguments.  Reason: " + exp.getMessage() );
            System.out.println();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java -jar app.jar", options );
            System.exit(1);
            return null;
        }
    }
	
}
