package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Cells interface.
 * A cell is a node in our dungeon. It has entrances and in some cases treasure.
 * A player can go through it to go to neighboring cells in his search of the end location.
 * Depending on the number of entrances, the cell can be either a Cave or a Tunnel.
 */
class Cell implements Cells {
  private final int[] coordinates;
  private List<Treasure> treasureList;
  private Otyugh otyugh;
  private final Map<Direction, Coordinates> neighborList;
  private int entrances;
  private boolean isCave;
  private boolean isTunnel;
  private int arrows;
  private Thief thief;
  private int stench;

  Cell(int[] coordinates) {
    if (coordinates.length < 2) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    this.coordinates = coordinates;
    this.entrances = 0;
    this.neighborList = new LinkedHashMap<>();
    this.treasureList = new ArrayList<>();
    this.otyugh = null;
    this.isCave = false;
    this.isTunnel = false;
    this.arrows = 0;
    this.thief = null;
    this.stench = 0;
  }

  Cell(Cell cell) {
    this.coordinates = cell.coordinates;
    this.entrances = cell.entrances;
    this.neighborList = cell.neighborList;
    this.treasureList = new ArrayList<>(cell.treasureList);
    if (cell.otyugh != null) {
      this.otyugh = new Otyugh(cell.otyugh);
    } else {
      this.otyugh = null;
    }
    this.isCave = cell.isCave;
    this.isTunnel = cell.isTunnel;
    this.arrows = cell.arrows;
    this.thief = cell.thief;
    this.stench = cell.stench;
  }

  @Override
  public int[] getCoordinates() {
    return coordinates;
  }

  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<>(treasureList);
  }

  void setTreasureList(Treasure t, int quantity) {
    for (int i = 0; i < quantity; i++) {
      this.treasureList.add(t);
    }
  }

  void emptyTreasure() {
    this.treasureList = new ArrayList<>();
  }

  @Override
  public Map<Direction, Coordinates> getNeighborList() {
    return new HashMap<>(neighborList);
  }

  void setNeighborList(Direction d, Coordinates coords) {
    neighborList.put(d, coords);
  }

  @Override
  public int getEntrances() {
    return entrances;
  }

  void setEntrances() {
    this.entrances = this.neighborList.size();
  }

  @Override
  public boolean getIsCave() {
    return this.isCave;
  }

  void setIsCave(boolean bool) {
    this.isCave = bool;
  }

  @Override
  public boolean getIsTunnel() {
    return this.isTunnel;
  }

  void setIsTunnel(boolean bool) {
    this.isTunnel = bool;
  }

  @Override
  public Otyugh getOtyugh() {
    return this.otyugh;
  }

  void setOtyugh(Otyugh o) {
    this.otyugh = o;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return Arrays.equals(coordinates, cell.coordinates);
  }

  @Override
  public int hashCode() {
    return 31 * Arrays.hashCode(coordinates);
  }

  @Override
  public int getArrows() {
    return this.arrows;
  }

  void setArrow(boolean add, int i) {
    if (add) {
      this.arrows += i;
    } else {
      this.arrows -= i;
    }
  }

  @Override
  public Thief getThief() {
    return this.thief;
  }

  void setThief(Thief thief) {
    this.thief = thief;
  }

  @Override
  public int getStench() {
    return this.stench;
  }

  void setStench(int stench) {
    this.stench = stench;
  }
  // end of class
}
