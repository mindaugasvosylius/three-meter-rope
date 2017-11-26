package game.event;

public class GameDto {

    private String id;

    private String name;

    private int currentPlayerSize;

    private int maxPlayerSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentPlayerSize() {
        return currentPlayerSize;
    }

    public void setCurrentPlayerSize(int currentPlayerSize) {
        this.currentPlayerSize = currentPlayerSize;
    }

    public int getMaxPlayerSize() {
        return maxPlayerSize;
    }

    public void setMaxPlayerSize(int maxPlayerSize) {
        this.maxPlayerSize = maxPlayerSize;
    }
}
