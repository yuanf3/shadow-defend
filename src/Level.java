import bagel.Input;
import bagel.Keys;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Level class. Handles most of the game behaviour
 */
public class Level {

    private static final String WAVE_FILE = "res/levels/waves.txt";
    private static final int MILLISEC_TO_SEC = 1000;

    private final Map<Integer, List<Event>> waves;
    private final TiledMap map;
    private final List<Point> polyline;
    private final List<Slicer> slicers;
    private final List<Tower> towers;
    private final List<Projectile> projectiles;
    private final List<Airplane> airplanes;
    private final BuyPanel bPanel;
    private final StatusPanel sPanel;
    private final Player player;

    private boolean levelFinished;

    private int currentWaveNumber;
    private int currentEventNumber;
    private double frameCount;
    private int repeats;
    private boolean waveStarted;

    /**
     * Creates a new Level object, which handles all game behaviour except time scale
     *
     * @param levelNumber The level number (either 1 or 2)
     */
    public Level(int levelNumber) {
        waves = new HashMap<>();
        map = new TiledMap("res/levels/" + levelNumber + ".tmx");
        polyline = map.getAllPolylines().get(0);

        slicers = new ArrayList<>();
        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
        airplanes = new ArrayList<>();
        player = new Player();
        bPanel = new BuyPanel();
        sPanel = new StatusPanel();

        levelFinished = false;
        currentWaveNumber = 0;
        readWavesFile();
        setWaveVariables();
    }

    /**
     * Reads the waves text file, populating the hashmap of wave events
     */
    private void readWavesFile() {
        try (BufferedReader in = new BufferedReader(new FileReader(WAVE_FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] columns = line.split(",");
                Integer waveNum = Integer.parseInt(columns[0]);
                List<Event> events = waves.computeIfAbsent(waveNum, k -> new ArrayList<>());
                events.add(new Event(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset variables for the start of the next wave
     */
    private void setWaveVariables() {
        currentWaveNumber++;
        currentEventNumber = 0;
        frameCount = Integer.MAX_VALUE;
        repeats = 0;
        waveStarted = false;
        projectiles.clear();
        ShadowDefend.setStatus("Awaiting Start");
    }

    private void drawPanels() {
        sPanel.drawPanel(currentWaveNumber, player.getLives());
        bPanel.drawPanel(player.getMoney());
    }

    /**
     * Spawns a Slicer at the start of the polyline
     *
     * @param spawnType String representing the type of Slicer to spawn in
     */
    public void spawnSlicer(String spawnType) {
        switch (spawnType) {
            case "slicer":
                slicers.add(new RegularSlicer(polyline.get(0), polyline, 1));
                break;
            case "superslicer":
                slicers.add(new SuperSlicer(polyline.get(0), polyline, 1));
                break;
            case "megaslicer":
                slicers.add(new MegaSlicer(polyline.get(0), polyline, 1));
                break;
            case "apexslicer":
                slicers.add(new ApexSlicer(polyline.get(0), polyline, 1));
                break;
            default:
                break;
        }
    }

    public boolean isLevelFinished() {
        return levelFinished;
    }

    /**
     * Updates the current state of the game. Keeps track of HUD, wave progress, game entities
     */
    public void update(Input input) {
        map.draw(0, 0, 0, 0, ShadowDefend.WIDTH, ShadowDefend.HEIGHT);

        if (input.wasPressed(Keys.S)) {
            if (!waveStarted) {
                waveStarted = true;
            }
        }

        // Update towers
        for (Tower tower : towers) {
            tower.update(slicers, projectiles);
        }

        // Update airplanes
        for (int i = airplanes.size() - 1; i >= 0; i--) {
            Airplane airplane = airplanes.get(i);
            airplane.update(slicers);
            if (airplane.isFinished()) {
                airplanes.remove(i);
            }
        }

        // Update projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.update(slicers);
            if (projectile.isCollided()) {
                projectiles.remove(i);
            }
        }

        if (waveStarted) {
            ShadowDefend.setStatus("Wave in Progress");

            Event currentEvent = waves.get(currentWaveNumber).get(currentEventNumber);
            int numRepeats = currentEvent.getNumRepeats();
            int duration = currentEvent.getDuration();
            String spawnType = currentEvent.getSpawnType();

            frameCount += ShadowDefend.getTimescale();
            if (frameCount / ShadowDefend.FPS * MILLISEC_TO_SEC >= duration) {
                if (repeats < numRepeats) {
                    spawnSlicer(spawnType);
                    repeats++;
                    frameCount = 0;
                }
            }

            // Update slicers
            for (int i = slicers.size() - 1; i >= 0; i--) {
                Slicer slicer = slicers.get(i);
                slicer.update();
                if (slicer.isDead()) {
                    int reward = slicer.getReward();
                    player.rewardMoney(reward);
                    slicer.spawnOnDeath(slicers);
                    slicers.remove(i);
                } else if (slicer.isFinished()) {
                    int penalty = slicer.getPenalty();
                    player.reduceLives(penalty);
                    slicers.remove(i);
                }
            }

            // Handle end of wave event
            if (repeats == numRepeats) {
                int numEventsInWave = waves.get(currentWaveNumber).size();
                if (currentEventNumber < numEventsInWave-1) {
                    // Move on to next wave event
                    repeats = 0;
                    currentEventNumber++;
                } else {
                    if (slicers.isEmpty()) {
                        int numWaves = waves.size();
                        if (currentWaveNumber < numWaves) {
                            int reward = 150 + currentWaveNumber * 100;
                            player.rewardMoney(reward);
                            setWaveVariables(); // End of wave
                        } else {
                            waveStarted = false;
                            levelFinished = true; // End of level
                        }
                    }
                }
            }
        }

        drawPanels();
        bPanel.update(map, player, towers, airplanes, input);
    }
}
