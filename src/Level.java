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

public class Level {

    private static final String WAVE_FILE = "res/levels/waves.txt";
    private static final int CONVERSION = 1000;

    private final Map<Integer, List<Event>> waves;
    private final TiledMap map;
    private final List<Point> polyline;
    private final List<Slicer> slicers;
    private final Player player;

    private boolean levelFinished;
    private int currentWaveNumber;
    private int currentEventNumber;
    private double frameCount;
    private int repeats;
    private boolean waveStarted;

    public Level(int levelNumber) {
        waves = new HashMap<>();
        map = new TiledMap("res/levels/" + levelNumber + ".tmx");
        polyline = map.getAllPolylines().get(0);
        slicers = new ArrayList<>();
        player = new Player();

        levelFinished = false;
        currentWaveNumber = 0;
        readWavesFile();
        setWaveStuff();
    }

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

    private void setWaveStuff() {
        currentWaveNumber++;
        currentEventNumber = 0;
        frameCount = Integer.MAX_VALUE;
        repeats = 0;
        waveStarted = false;
    }

    private void spawnSlicer(String spawnType) {
        switch (spawnType) {
            case "slicer":
                slicers.add(new RegularSlicer(polyline));
                break;
            case "superslicer":
                slicers.add(new SuperSlicer(polyline));
                break;
            case "megaslicer":
                slicers.add(new MegaSlicer(polyline));
                break;
            case "apexslicer":
                slicers.add(new ApexSlicer(polyline));
                break;
            default:
                break;
        }
    }

    public boolean levelComplete() {
        return levelFinished;
    }

    public void update(Input input) {
        map.draw(0, 0, 0, 0, ShadowDefend.WIDTH, ShadowDefend.HEIGHT);

        if (input.wasPressed(Keys.S)) {
            waveStarted = true;
        }

        if (waveStarted) {
            Event currentEvent = waves.get(currentWaveNumber).get(currentEventNumber);
            int numRepeats = currentEvent.getNumRepeats();
            int duration = currentEvent.getDuration();
            String spawnType = currentEvent.getSpawnType();

            frameCount += ShadowDefend.getTimescale();
            if (frameCount / ShadowDefend.FPS * CONVERSION >= duration && repeats != numRepeats) {
                spawnSlicer(spawnType);
                repeats++;
                frameCount = 0;
            }

            for (int i = slicers.size() - 1; i >= 0; i--) {
                Slicer slicer = slicers.get(i);
                slicer.update(input);
                if (slicer.isDead()) {
                    int reward = slicer.getReward();
                    player.rewardMoney(reward);
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
                            setWaveStuff(); // End of wave
                        } else {
                            levelFinished = true; // End of level
                        }
                    }
                }
            }
        }

    }
}