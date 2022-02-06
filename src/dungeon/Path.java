package dungeon;

class Path {
  private final Cell src;
  private final Cell dest;
  private final Direction srcToDest;
  private final Direction destToSrc;

  Path(Cell c1, Cell c2, Direction sd, Direction ds) {
    this.src = c1;
    this.dest = c2;
    this.srcToDest = sd;
    this.destToSrc = ds;
  }

  Cell getSrc() {
    return src;
  }

  Cell getDest() {
    return dest;
  }

  Direction getSrdToDest() {
    return srcToDest;
  }

  Direction getDestToSrc() {
    return destToSrc;
  }
}
