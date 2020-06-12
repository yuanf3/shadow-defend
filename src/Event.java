public class Event {

    private int numRepeats;
    private String spawnType;
    private int duration;

    /**
     * Creates a new Event
     *
     * @param line A line of text read from the waves text file
     */
    public Event(String line) {
        String[] columns = line.split(",");
        // Spawn event
        if (columns[1].equals("spawn")) {
            numRepeats = Integer.parseInt(columns[2]);
            spawnType = columns[3];
            duration = Integer.parseInt(columns[4]);
        }
        // Delay event
        else if (columns[1].equals("delay")) {
            numRepeats = 1;
            spawnType = "";
            duration = Integer.parseInt(columns[2]);
        }
    }

    public int getNumRepeats() {
        return numRepeats;
    }

    public String getSpawnType() {
        return spawnType;
    }

    public int getDuration() {
        return duration;
    }
}
