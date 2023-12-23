package org.bertural.deepforge;

import org.apache.commons.cli.*;
import org.bertural.deepforge.data.Authentication;
import org.bertural.deepforge.data.Database;
import org.bertural.deepforge.telnet.TelnetServer;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

public class Main {
    private static Logger logger = LoggerFactory.logger(Main.class);

    private static final String APP_NAME = "deepforge";

    // Command line parsing
    private static Options options = null;
    private static CommandLineParser parser = null;
    private static HelpFormatter formatter = null;

    // Basic configurations
    private static int    telnetPort   = 6969;
    private static String databaseFile = APP_NAME + ".db";


    public static void main(String[] args) {
        if (!parseCommandLineArguments(args))
            System.exit(1);

        Database.getInstance().configure(databaseFile);

        TelnetServer server = new TelnetServer(telnetPort);
        server.start();
    }


    /** As the name suggests, parse the command line arguments */
    private static boolean parseCommandLineArguments(String[] args) {
        options = initOptions();
        parser = new DefaultParser();
        formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                formatter.printHelp(APP_NAME, options);
                System.exit(0);
            }
            if (cmd.hasOption("port")) {
                telnetPort = Integer.parseInt(cmd.getOptionValue("port"));
            }
            if (cmd.hasOption("database")) {
                databaseFile = cmd.getOptionValue("database");
            }

        } catch (ParseException e) {
            logger.error("Unable to parse given arguments");
            formatter.printHelp(APP_NAME, options);
            return false;
        }
        return true;
    }

    /** Initialize command line argument options */
    private static Options initOptions() {
        Option help = new Option("h", "help", false, "print this message");
        Option port = Option.builder("p").longOpt("port").argName("port-number").hasArg().desc("start telnet server on given port-number").build();
        Option db = Option.builder("db").longOpt("database").argName("file").hasArg().desc("load given database file").build();

        Options options = new Options();
        options.addOption(help).addOption(port).addOption(db);

        return options;
    }
}
