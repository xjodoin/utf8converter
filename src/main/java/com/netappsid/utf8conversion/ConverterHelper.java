/**
 * 
 */
package com.netappsid.utf8conversion;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class ConverterHelper
{
	public static Options constructGnuOptions()
	{
		final Options gnuOptions = new Options();
		gnuOptions.addOption("h", "help", false, "Help").addOption("i", "input", true, "Input file encoding")
				.addOption("o", "ouput", true, "Output file encoding").addOption("d", "dir", true, "Directory");
		return gnuOptions;
	}

	/**
	 * Write "help" to the provided OutputStream.
	 */
	public static void printHelp(final Options options, final int printedRowWidth, final String header, final String footer, final int spacesBeforeOption,
			final int spacesBeforeOptionDescription, final boolean displayUsage, final OutputStream out)
	{
		final String commandLineSyntax = "java -jar utf8convertion.jar";
		final PrintWriter writer = new PrintWriter(out);
		final HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp(writer, printedRowWidth, commandLineSyntax, header, options, spacesBeforeOption, spacesBeforeOptionDescription, footer,
				displayUsage);
		writer.close();
	}

	public static void useGnuParser(final String[] commandLineArguments)
	{
		final CommandLineParser cmdLineGnuParser = new GnuParser();

		final Options gnuOptions = constructGnuOptions();
		CommandLine commandLine;
		try
		{
			commandLine = cmdLineGnuParser.parse(gnuOptions, commandLineArguments);

			if (commandLineArguments.length < 1 || commandLine.hasOption("help"))
			{
				printHelp(gnuOptions, 80, "Torpedo UTF-8 Converter", "", 3, 5, true, System.out);
			}
			else
			{
				Conversion conversion = new Conversion();

				if (commandLine.hasOption("i"))
				{
					conversion.setInputEncoding(commandLine.getOptionValue("i"));
				}
				if (commandLine.hasOption("o"))
				{
					conversion.setOutputEncoding(commandLine.getOptionValue("o"));
				}
				if (commandLine.hasOption("d"))
				{
					conversion.setDir(commandLine.getOptionValue("d"));
				}

				conversion.convert();
			}
		}
		catch (ParseException parseException) // checked exception
		{
			System.err.println("Encountered exception while parsing using GnuParser:\n" + parseException.getMessage());
		}
	}
}
