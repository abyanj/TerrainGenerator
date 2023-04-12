# Assignment A2 and A3: Mesh Generator and Terrain Generator

- Author #1 rahmam88@mcmaster.ca
- Author #2 hursts4@mcmaster.ca
- Author #3 jaigia1@mcmaster.ca

## How to install?

```
mvn install
```

It creates two jars:

1. `generator/generator.jar` to generate meshes
2. `visualizer/visualizer.jar` to visualize such meshes as SVG files

## Examples of execution

### Generating a mesh, grid or irregular

```
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o img/grid.mesh
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -p 500 -r 1 -o img/irregular.mesh
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Terrain generation of a mesh

```
java -jar island/island.jar -i img/irregular.mesh -o img/irregularisland.mesh -s circle -e volcano -soil clay -biome canada -seed 22
```

1. shape (-s) arguments: `circle, square` (REQUIRED)
2. elevation (-e) arguments: `volcano, hill, lagoon` (REQUIRED)
3. rivers (-rivers) arguments: `# of rivers (defaults to random # of rivers)` (NOT REQUIRED)
4. lakes (-lakes) arguments: `# of lakes (defaults to random # of lakes)` (NOT REQUIRED)
5. aquifers (-aquifers) arguments: `# of aquifers (defaults to random # of aquifers)` (NOT REQUIRED)
6. soil profile (-soil) arguments: `sand, clay, loam, Incorrect arguments(defaults to loam)` (REQUIRED)
7. biome (-biome) arguments: `canada, indonesia, Incorrect arguments(defaults to canada)` (REQUIRED)
8. heatmap (-heatmap) arguments: `altitude, absorption` (NOT REQUIRED)

To test seed reproducibility, user must keep lakes/rivers/aquifers count consistent, or not include them, which will result in the seed generating them automatically.
For example: `-s square -e volcano -soil clay -biome canada -lakes 1 -rivers 2 -aquifers 3 -seed 10 `
To test the seed while specificying lakes/rivers/aquifers, # of lakes, rivers, aquifers must be kept at 1, 2, 3

For example: `-s square -e volcano -soil clay -biome canada -seed 10`
To test the seed, you'd use the same command and # of lakes/aquifers/rivers will automically be generated and kept consistent for the seed.



### Visualizing a mesh, (regular or debug mode)

```
java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid.svg
java -jar visualizer/visualizer.jar -i img/grid.mesh -o img/grid_debug.svg -x
java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg
java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular_debug.svg -x

java -jar visualizer/visualizer.jar -i img/irregularisland.mesh -o img/irregularisland.svg
```

Note: PDF versions of the SVG files were created with `rsvg-convert`.

## Backlog

### Definition of Done 

Feature is completed when it is the final version, fully implemented  with no further reiterations required.

### A2 Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Generating and visualizing horizontal and vertical line segments between adjacent vertices | Abyan Sylvia Farhan | 02/05/2022 | 02/19/2022 | Done |
| F02 | Two decimal place precision model for floating point numbers | Sylvia | 02/21/2023 | 02/21/2023 | Done |
| F03 | Creating polygons with centroids and neighbourhood relationships | Farhan, Abyan | 02/21/2023 | 02/26/2023 | Done |
| F04 | Rendering with varying thickness and transparency values | Abyan, Farhan | 02/23/2023 | 02/26/2023 | Done |
| F05 | Debug mode to set all colours to a consistent scheme | Sylvia | 02/23/2023 | 02/24/2023 | Done |

### A3 Product Backlog

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Shapes | Sylvia, Farhan, Abyan | 03/11 | 03/13 | Done |
| F02 | Choice of shape | Sylvia | 03/14 | 03/14 | Done |
| F03 | Seeds | Farhan | 03/18 | 03/18 | Done |
| F04 | Elevation | Abyan | 03/15 | 03/21 | Done |
| F05 | Choice of altimetric profile | Abyan | 03/20 | 03/22 | Done |
| F06 | Lakes | Farhan | 03/21 | 03/22 | Done |
| F07 | Choice of number of lakes | Farhan | 03/22 | 03/22 | Done |
| F08 | Aquifers | Sylvia | 03/22 | 03/22 | Done |
| F09 | Choice of number of aquifers | Sylvia | 03/22 | 03/22 | Done |
| F10 | Rivers | Sylvia | 03/23 | 03/24 | Done |
| F11 | Choice of number of rivers | Sylvia | 03/23 | 03/24 | Done |
| F12 | Soil moisture | Abyan | 03/23 | 03/25 | Done |
| F13 | Choice of soil absorption profile | Abyan | 03/24 | 03/25 | Done |
| F14 | Biomes | Farhan | 03/25 | 03/26 | Done |
| F15 | Choice of Whittaker diagram | Farhan | 03/25 | 03/26 | Done |
| F16 | Heatmaps | Abyan | 03/11 | 03/26 | Done |


