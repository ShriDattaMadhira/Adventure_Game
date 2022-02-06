package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A network of tunnels and caves that are interconnected so that player can explore the entire
 * world by traveling from cave to cave through the tunnels that connect them.
 */
abstract class DungeonImpl implements Dungeon {
  PlayerImpl player;
  Cell start;
  Cell end;
  Cell[][] dungeon;
  boolean gameOver;
  List<Cells> visited;
  private final int width;
  private final int height;
  private final int interconnectivity;
  private final boolean wrapping;
  private final int treasurePcnt;

  /**
   * Constructs a dungeon for the player to play the game.
   * @param width - width of the dungeon.
   * @param height - height of the dungeon.
   * @param wrap - wrap the edge cases.
   * @param interconnectivity - interconnectivity in the dungeon.
   * @param treasurePcnt - percentage of cells with treasure.
   */
  DungeonImpl(int width, int height, boolean wrap, int interconnectivity, int treasurePcnt) {
    if (width <= 0) {
      throw new IllegalArgumentException("Width cannot be < or = 0.");
    }
    if (height <= 0) {
      throw new IllegalArgumentException("Height cannot be < or = 0.");
    }
    if (width <= 4 && height <= 4) {
      throw new IllegalArgumentException("Dungeon is too small.");
    }
    if (interconnectivity < 0 || interconnectivity >= width * height) {
      throw new IllegalArgumentException("Invalid Interconnectivity.");
    }
    if (treasurePcnt < 0) {
      throw new IllegalArgumentException("Treasure percentage cannot be < 0.");
    }
    this.width = width;
    this.height = height;
    this.interconnectivity = interconnectivity;
    this.wrapping = wrap;
    this.treasurePcnt = treasurePcnt;

    this.gameOver = false;
    createDungeon(width, height, wrap, interconnectivity, treasurePcnt);
    player = new PlayerImpl();
    initPosition();
    this.visited =
            new ArrayList<>(List.of(dungeon[start.getCoordinates()[0]][start.getCoordinates()[1]]));
  }

  // Creates a dungeon using several helper methods in the process.
  private void createDungeon(int width, int height, boolean wrap, int interconnectivity,
                             int treasurePercentage) {
    // creating a matrix of cells.
    dungeon = new Cell[height][width];
    initializeDungeon();

    // get possible paths.
    List<Path> possiblePaths = getPossiblePaths(wrap);
    //System.out.println("Number of possible paths: " + possiblePaths.size());

    // Apply kruskal's as per video.
    convertDungeon(possiblePaths, width, height, interconnectivity);

    // update caves and tunnels
    updateCavesNTunnels();

    // get start and end locations
    getStartNEnd();

    // put treasure in the dungeon.
    putTreasure(treasurePercentage);
  }

  // Initialize the dungeon with Cells.
  private void initializeDungeon() {
    for (int i = 0; i < dungeon.length; i++) {
      for (int j = 0; j < dungeon[0].length; j++) {
        dungeon[i][j] = new Cell(new int[]{i, j});
      }
    }
  }

  // Returns all the possible paths in the dungeon depending on wrapping/non-wrapping.
  private List<Path> getPossiblePaths(boolean wrap) {
    List<Path> paths = new ArrayList<>();

    for (int i = 0; i < dungeon.length; i++) {
      for (int j = 0; j < dungeon[0].length; j++) {

        Path p = makePath(Direction.NORTH, Direction.SOUTH, i, j, wrap);
        if (p != null) {
          if (!inPaths(p, paths)) {
            paths.add(p);
          }
        }
        p = makePath(Direction.SOUTH, Direction.NORTH, i, j, wrap);
        if (p != null) {
          if (!inPaths(p, paths)) {
            paths.add(p);
          }
        }
        p = makePath(Direction.EAST, Direction.WEST, i, j, wrap);
        if (p != null) {
          if (!inPaths(p, paths)) {
            paths.add(p);
          }
        }
        p = makePath(Direction.WEST, Direction.EAST, i, j, wrap);
        if (p != null) {
          if (!inPaths(p, paths)) {
            paths.add(p);
          }
        }

      }
    }
    return paths;
  }

  // Check if the current path is already in possible paths list. Avoid duplicates or reverse paths.
  private boolean inPaths(Path p, List<Path> paths) {
    for (Path path: paths) {
      if (path.getSrc().equals(p.getSrc()) && path.getDest().equals(p.getDest())
              || path.getSrc().equals(p.getDest()) && path.getDest().equals(p.getSrc())) {
        return true;
      }
    }
    return false;
  }

  // Create a path object with source & destination and add it to possiblePaths list.
  private Path makePath(Direction direction, Direction reverse, int i, int j, boolean wrap) {
    if (direction.getX() != 0) {
      int nextX = i + direction.getX();
      if (nextX == dungeon.length) {
        if (wrap) {
          return new Path(dungeon[i][j], dungeon[0][j], direction, reverse);
        } else {
          return null;
        }
      } else if (nextX < 0) {
        if (wrap) {
          return new Path(dungeon[i][j], dungeon[dungeon.length - 1][j], direction, reverse);
        } else {
          return null;
        }
      } else {
        return new Path(dungeon[i][j], dungeon[nextX][j], direction, reverse);
      }
    } else { // if (direction.getY() != 0)
      int nextY = j + direction.getY();
      if (nextY == dungeon[0].length) {
        if (wrap) {
          return new Path(dungeon[i][j], dungeon[i][0], direction, reverse);
        } else {
          return null;
        }
      } else if (nextY < 0) {
        if (wrap) {
          return new Path(dungeon[i][j], dungeon[i][dungeon[0].length - 1], direction, reverse);
        } else {
          return null;
        }
      } else {
        return new Path(dungeon[i][j], dungeon[i][nextY], direction, reverse);
      }
    }
  }

  // Convert the dungeon into MST using the updated kruskal's algorithm from the video.
  private List<Path> convertDungeon(List<Path> possPaths, int w, int h, int interConnect) {
    Random rand = new Random(0);
    List<Path> selectedPaths = new ArrayList<>();
    List<Path> leftOver = new ArrayList<>();
    List<List<Cell>> set = new ArrayList<>();

    // iterate until selectedPaths.size() <= number of vertices - 1.
    while (selectedPaths.size() < (w * h) - 1) {
      // pick a random path from possPaths.
      Path randPath = possPaths.get(rand.nextInt(possPaths.size()));
      if (selectedPaths.contains(randPath)) {
        randPath = possPaths.get(rand.nextInt(possPaths.size()));
      }
      // check if source and destination in same set. if not, add to selectedPaths.
      if (!checkSet(set, randPath.getSrc(), randPath.getDest())) {
        selectedPaths.add(randPath);
        set = addToSet(set, randPath.getSrc(), randPath.getDest());
      }
    }
    // add the remaining paths to leftOver paths.
    for (Path p: possPaths) {
      if (!selectedPaths.contains(p) ) {
        leftOver.add(p);
      }
    }
    // adding the extra edges according to desired interconnectivity.
    int temp = interConnect;
    for (Path path : leftOver) {
      selectedPaths.add(path);
      temp -= 1;
      if (temp == 0) {
        break;
      }
    }
    //System.out.println("Number of selected paths after interconnectivity: "+selectedPaths.size());
    //System.out.println("Number of left over paths after interconnectivity: "
    // + (leftOver.size()-interConnect));

    // update neighbors in cells.
    for (Path path : selectedPaths) {
      Cell src = path.getSrc();
      Direction sd = path.getSrdToDest();
      Cell dest = path.getDest();
      Direction ds = path.getDestToSrc();

      src.setNeighborList(sd, new Coordinates(dest.getCoordinates()[0], dest.getCoordinates()[1]));
      src.setEntrances();

      dest.setNeighborList(ds, new Coordinates(src.getCoordinates()[0], src.getCoordinates()[1]));
      dest.setEntrances();
    }

    return selectedPaths;
  }

  // Helper method to see if source and destination are in same or different sets.
  private boolean checkSet(List<List<Cell>> set, Cell src, Cell dest) {
    if (set.size() == 0) {
      return false;
    } else {
      int srcListIndex = -1;
      int destListIndex = -1;
      for (List<Cell> cellList : set) {
        if (cellList.contains(src)) {
          srcListIndex = set.indexOf(cellList);
        }
        if (cellList.contains(dest)) {
          destListIndex = set.indexOf(cellList);
        }
      }
      return srcListIndex == destListIndex;
    }
  }

  // Helper method that adds the cells to appropriate sets.
  private List<List<Cell>> addToSet(List<List<Cell>> set, Cell src, Cell dest) {
    if (set.size() == 0) {
      set.add(new ArrayList<>() {
        {
          add(src);
          add(dest);
        }
      });
    } else {
      int srcListIndex = -1;
      int destListIndex = -1;
      for (List<Cell> cellList : set) {
        if (cellList.contains(src)) {
          srcListIndex = set.indexOf(cellList);
        }
        if (cellList.contains(dest)) {
          destListIndex = set.indexOf(cellList);
        }
      }
      // if both are not there in any list.
      if (srcListIndex == -1 && destListIndex == -1) {
        set.add(new ArrayList<>() {
          {
            add(src);
            add(dest);
          }
        });
      } else if (srcListIndex == -1) {
        // if only destination is present in a list.
        set.get(destListIndex).add(src);
      } else if (destListIndex == -1) {
        // if only source is present in a list.
        set.get(srcListIndex).add(dest);
      } else if (srcListIndex != destListIndex) {
        set.get(srcListIndex).addAll(set.get(destListIndex));
      }
    }
    return set;
  }

  // Update Cell to be a cave or a tunnel based on the number of entrances.
  private void updateCavesNTunnels() {
    for (Cell[] cells : dungeon) {
      for (Cell cell : cells) {
        int entrances = cell.getEntrances();
        if (entrances == 1 || entrances == 3 || entrances >= 4) {
          cell.setIsCave(true);
        } else if (entrances == 2) {
          cell.setIsTunnel(true);
        }
      }
    }
  }

  // get viable cells. Cells with entrances = 1,3,4. (Caves).
  List<Cell> getAllCaves() {
    List<Cell> caves = new ArrayList<>();
    for (Cell[] cells : dungeon) {
      for (Cell cell : cells) {
        if (cell.getIsCave()) {
          caves.add(cell);
        }
      }
    }
    return caves;
  }

  // Put treasure in the caves depending on the treasure percentage.
  private void putTreasure(int treasurePercentage) {
    Random rand = new Random(0);
    List<Cell> caves = getAllCaves();
    //System.out.println("Number of caves (Entrances# = 1,3,4) are: " + caves.size());
    // Count the caves and divide them by percentage to get the no that should have treasure.
    int cavesWithTreasure = (int) Math.ceil((caves.size() * treasurePercentage) / 100.0);
    //System.out.println("Number of caves with treasure allowed: " + cavesWithTreasure);
    // randomly pick a cave and update its treasureList.
    for (int i = 0; i < cavesWithTreasure; i++) {
      Cell cave = caves.get(rand.nextInt(caves.size()));
      cave.setTreasureList(Treasure.SAPPHIRE,  1);
      cave.setTreasureList(Treasure.DIAMONDS,  2);
      cave.setTreasureList(Treasure.RUBIES,  3);
    }
  }

  // Get start and end cave for the player to play the game. Distance should be > 5.
  private void getStartNEnd() {
    Random rand = new Random(0);
    // get starting location and ending location.
    start = dungeon[rand.nextInt(dungeon.length)][rand.nextInt(dungeon[0].length)];
    while (start.getIsTunnel()) {
      start = dungeon[rand.nextInt(dungeon.length)][rand.nextInt(dungeon[0].length)];
    }

    List<Cell> farthest = new ArrayList<>();
    double distance = 0.0;
    for (Cell[] cellList: dungeon) {
      for (Cell cell: cellList) {
        // check distance between starting and ending location using euclidean/manhattan distance.
        distance = Math.sqrt(Math.pow((cell.getCoordinates()[0] - start.getCoordinates()[0]), 2)
                + (Math.pow((cell.getCoordinates()[1] - start.getCoordinates()[1]), 2)));
        if (distance >= 5) {
          farthest.add(cell);
        }
      }
    }
    if (farthest.size() == 0) {
      throw new IllegalArgumentException("Cannot find the start & end nodes as per rules given.");
    } else {
      end = farthest.get(rand.nextInt(farthest.size()));
      while (end.getIsTunnel()) {
        end = farthest.get(rand.nextInt(farthest.size()));
      }
    }
  }

  // Set player's initial position.
  private void initPosition() {
    // put player in the starting location.
    player.setPosition(new Coordinates(start.getCoordinates()[0], start.getCoordinates()[1]));
  }

  // add visited cells in the dungeon to visited list.
  void addToVisited(Cells cell) {
    if (!visited.contains(cell)) {
      visited.add(cell);
    }
  }

  @Override
  public void move(Direction direction) {
    if (direction != Direction.NORTH && direction != Direction.SOUTH && direction != Direction.WEST
            && direction != Direction.EAST) {
      throw new IllegalArgumentException("Invalid Direction specified.");
    }
    if (calcNextLocation(direction)) {
      if (Arrays.equals(new int[]{ player.getPosition().getX(), player.getPosition().getY()},
              end.getCoordinates())) {
        this.gameOver = true;
      }
    }
  }

  @Override
  public void pick(boolean pickTreasure, boolean pickArrows) {
    Cell curr = dungeon[player.getPosition().getX()][player.getPosition().getY()];
    if (pickTreasure) {
      player.setBag(curr.getTreasureList());
      curr.emptyTreasure();
    }
  }

  // calculates the next location, moves the player and sets the player's treasure bag.
  private boolean calcNextLocation(Direction direction) {
    Cell curr = dungeon[player.getPosition().getX()][player.getPosition().getY()];
    if (curr.getNeighborList().containsKey(direction)) {
      Coordinates next = curr.getNeighborList().get(direction);
      // Cell nextCell = dungeon[next.getX()][next.getY()];
      player.setPosition(next);
      return true;
    } else {
      return false;
    }
  }

  void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  @Override
  public boolean isGameOver() {
    return this.gameOver;
  }

  @Override
  public List<Cells> getVisited() {
    // This function is only used by the view which does not mutate any objects. That is why no copy
    // is made for this before sending the list.
    return this.visited;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public boolean getWrapping() {
    return this.wrapping;
  }

  @Override
  public int getInterconnectivity() {
    return this.interconnectivity;
  }

  @Override
  public int getPercentage() {
    return this.treasurePcnt;
  }

  @Override
  public Cell[][] getDungeon() {
    Cell[][] copy = new Cell[dungeon.length][dungeon[0].length];
    for (int i = 0; i < dungeon.length; i++) {
      for (int j = 0; j < dungeon[0].length; j++) {
        copy[i][j] = new Cell(dungeon[i][j]);
      }
    }
    return copy;
  }

  @Override
  public int[] getCurrPlayerPosition() {
    return new int[] {player.getPosition().getX(), player.getPosition().getY()};
  }

  @Override
  public boolean getEaten() {
    return false;
  }

  @Override
  public int[] getStartLocation() {
    return start.getCoordinates();
  }

  @Override
  public int[] getEndLocation() {
    return end.getCoordinates();
  }

  // end of class.
}
