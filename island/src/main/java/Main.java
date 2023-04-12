import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.adt.TerrainMesh;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Seed;
import ca.mcmaster.cas.se2aa4.a3.island.specification.biome.BiomeSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.SpecificationFactory;
import ca.mcmaster.cas.se2aa4.a3.island.specification.aquifer.AquiferSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.elevation.Elevationable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.lake.LakeSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.river.RiverSpecification;
import ca.mcmaster.cas.se2aa4.a3.island.specification.shape.Shapable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.soil.Soilable;
import ca.mcmaster.cas.se2aa4.a3.island.specification.biome.Biomable;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Seed seed;
        if (config.seedProvided()){
            seed = new Seed(config.seed());
            // Check of seed has a zero or one and if true then increase to nearest possible seed
            // Because 1 or 10 or 11 or 0 are not valid seeds
            if (seed.containsZeroOrOne(seed.getSeed()) || seed.getSeed() == 0){
                seed = new Seed(seed.validateSeed(seed.getSeed()));
            }
        } else {
            seed = new Seed();
        }
        Mesh inputMesh = new MeshFactory().read(config.input());
        Mesh outputMesh;


        //Random Number Generator for any # features not provided by user
        Random rng = new Random();

        //TerrainMesh to work with
        TerrainMesh terrainMesh = new TerrainMesh(inputMesh);

        //Build the shape
        Shapable shapableSpec = SpecificationFactory.createShapable(config, seed);
        terrainMesh = shapableSpec.buildShape(terrainMesh);
        terrainMesh.addIslandTiles();


        //Apply elevation
        Elevationable elevatableSpec = SpecificationFactory.createElevationable(config, seed);
        terrainMesh = elevatableSpec.applyElevation(terrainMesh);
        terrainMesh.stabilizeAltitude();
        if (config.lakesProvided()){
            LakeSpecification lakeSpec = new LakeSpecification(seed, config.lakes());
            terrainMesh = lakeSpec.addLakes(terrainMesh);
        } else {
            LakeSpecification lakeSpec = new LakeSpecification(seed, -1);
            terrainMesh = lakeSpec.addLakes(terrainMesh);
        }

        //Add aquifers
        AquiferSpecification aquiferSpec;
        if (config.numAquifersProvided()){
            aquiferSpec = new AquiferSpecification(seed, config.numAquifers());
        } else {
            aquiferSpec = new AquiferSpecification(seed, rng.nextInt(15));
        }
        terrainMesh = aquiferSpec.addAquifers(terrainMesh);

        //Add rivers
        RiverSpecification riverSpec;
        if (config.numRiversProvided()){
            riverSpec = new RiverSpecification(seed, config.numRivers());
        } else {
            riverSpec = new RiverSpecification(seed, rng.nextInt(15));
        }
        terrainMesh = riverSpec.addRivers(terrainMesh);


        //Apply Soil properties
        Soilable SoilableSpec = SpecificationFactory.createSoilable(config);
        terrainMesh = SoilableSpec.applySoilAbsorption(terrainMesh);

        Biomable BiomableSpec = SpecificationFactory.createBiomable(config);
        terrainMesh = BiomableSpec.biomeCreate(terrainMesh);



        //Final rebuild of Mesh
        if (config.heatmapProvided()){
            switch (config.heatmap()){
                case "altitude":
                    terrainMesh.calculateAltitude();
                    outputMesh = terrainMesh.addAltitudeColor(inputMesh);
                    break;
                case "aquifer":
                    outputMesh = terrainMesh.addAquiferColor(inputMesh);
                    break;
                case "absorption":
                    terrainMesh.calculateAbsorption();
                    outputMesh = terrainMesh.addAbsorptionColor(inputMesh);
                    break;
                //Add more heatmap options as more cases
                default:
                    terrainMesh.calculateBiome(config.biome()); //null arg until Whittaker Diagrams implemented
                    outputMesh = terrainMesh.addColor(inputMesh, config.city());
                    break;
            }
        } else {
            terrainMesh.calculateBiome(config.biome()); //null arg until Whittaker Diagrams implemented
            outputMesh = terrainMesh.addColor(inputMesh, config.city());
        }

        new MeshFactory().write(outputMesh, config.output());
    }
}