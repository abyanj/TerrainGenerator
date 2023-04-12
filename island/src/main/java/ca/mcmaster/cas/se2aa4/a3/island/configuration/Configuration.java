package ca.mcmaster.cas.se2aa4.a3.island.configuration;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    //List of option names
    public static final String INPUT = "i";
    public static final String OUTPUT = "o";
    public static final String SHAPE = "s";
    public static final String DEBUG = "d";
    public static final String HELP = "help";
    public static final String SEED = "seed";
    public static final String ELEVATION = "e";
    public static final String HEATMAP = "heatmap";
    public static final String LAKES = "lakes";
    public static final String AQUIFERS = "aquifers";
    public static final String RIVERS = "rivers";
    public static final String SOIL = "soil";
    public static final String BIOME = "biome";
    public static final String CITY = "city";

    private CommandLine cli;

    //Constructor
    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    //Help message to print if incorrect syntax used
    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar island.jar", options());
        System.exit(0);
    }

    //Export HashMap containing all chosen options
    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    //Get just the specified chosen option
    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.mesh");
    }

    public boolean debug() { return this.cli.hasOption(DEBUG); }

    public boolean seedProvided() { return this.cli.hasOption(SEED); }

    public String seed() { return this.cli.getOptionValue(SEED); }

    public boolean heatmapProvided() { return this.cli.hasOption(HEATMAP); }

    public String heatmap() { return this.cli.getOptionValue(HEATMAP); }

    public boolean lakesProvided() {return this.cli.hasOption(LAKES); }

    public int lakes() {return Integer.parseInt(this.cli.getOptionValue(LAKES));}

    public int city() {return Integer.parseInt(this.cli.getOptionValue(CITY));}

    public boolean numAquifersProvided() { return this.cli.hasOption(AQUIFERS); }

    public int numAquifers() { return Integer.parseInt(this.cli.getOptionValue(AQUIFERS)); }

    public boolean numRiversProvided() { return this.cli.hasOption(RIVERS); }

    public int numRivers() { return Integer.parseInt(this.cli.getOptionValue(RIVERS)); }
    public String biome(){return this.cli.getOptionValue(BIOME);}

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (MESH)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(SHAPE, true, "Shape of the island"));
        options.addOption(new Option(DEBUG, false, "Debug mode"));
        options.addOption(new Option(HELP, false, "print help message"));
        options.addOption(new Option(SEED, true, "Seed number to generate"));
        options.addOption(new Option(ELEVATION, true, "Elevation of the island"));
        options.addOption(new Option(LAKES, true, "Number of lakes on the island"));
        options.addOption(new Option(AQUIFERS, true, "Number of aquifers on the island"));
        options.addOption(new Option(RIVERS, true, "Number of rivers on the island"));
        options.addOption(new Option(SOIL, true, "Soil profile of island"));
        options.addOption(new Option(BIOME, true, "Specified biome to create"));
        options.addOption(new Option(HEATMAP, true, "Display a heatmap instead of biomes"));
        options.addOption(new Option(CITY, true, "Number of Cities"));
        return options;
    }
}