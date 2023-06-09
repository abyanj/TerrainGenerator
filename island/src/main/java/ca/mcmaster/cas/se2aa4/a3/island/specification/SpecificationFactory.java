package ca.mcmaster.cas.se2aa4.a3.island.specification;

import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;
import ca.mcmaster.cas.se2aa4.a3.island.specification.biome.Biomable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.biome.BiomeSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.Elevationable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.HillSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.LagoonSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.VolcanoSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.shape.*;
import ca.mcmaster.cas.se2aa4.a3.island.specification.soil.SoilSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.soil.Soilable;

import java.util.HashMap;
import java.util.Map;

public class SpecificationFactory {

    //Relates the chosen option to the specific class that builds for that option
    private static final Map<String, Class> bindings = new HashMap<>();

    static {
        bindings.put("circle", CircleSpecification.class);
        bindings.put("square", SquareSpecification.class);
        bindings.put("volcano", VolcanoSpecification.class);
        bindings.put("hill", HillSpecification.class);
        bindings.put("lagoon", LagoonSpecification.class);

    }

    //Sets up a mesh to obtain its shape
    public static Shapable createShapable(Configuration configuration, Seed seed) {
        Map<String, String> options = configuration.export();
        try {
            Class klass = bindings.get(options.get(Configuration.SHAPE));
            return (Shapable) klass.getDeclaredConstructor(Seed.class).newInstance(seed);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }
    public static Soilable createSoilable(Configuration configuration) {
        Map<String, String> options = configuration.export();
        try {
            String soilType = options.get(Configuration.SOIL);
            if (soilType.equalsIgnoreCase("sand")) {
                return new SoilSpecification(0.75);
            } else if (soilType.equalsIgnoreCase("clay")) {
                return new SoilSpecification(1.15);
            } else if (soilType.equalsIgnoreCase("loam")) {
                return new SoilSpecification(1.5);
            } else { // Default to loamy soil
                System.out.println("Default Soil: Loam");
                return new SoilSpecification(1.5);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Elevationable createElevationable(Configuration configuration, Seed seed) {
        Map<String, String> options = configuration.export();
        try {
            Class klass = bindings.get(options.get(Configuration.ELEVATION));
            return (Elevationable) klass.getDeclaredConstructor(Seed.class).newInstance(seed);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }
    public static Biomable createBiomable(Configuration configuration) {
        Map<String, String> options = configuration.export();
        try {
            String biomeType = options.get(Configuration.BIOME);
            if (biomeType.equalsIgnoreCase("indonesia")) {
                return new BiomeSpecification(biomeType);
            } else if(biomeType.equalsIgnoreCase("canada")){
                return new BiomeSpecification(biomeType);
            } else { // Default to loamy soil
                System.out.println("Default Biome: Canada");
                biomeType = "canada";
                return new BiomeSpecification(biomeType);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    /*
    Note: Shape doesn't take parameters, but if a specification does need a parameter
    ex. # lakes, then do klass.getDeclaredConstructor(Map.class).newInstance(options) and have
    the constructor of that class parse the parameters from options (see how GridSpecification does it)
     */

    //Future setups get added here

}
