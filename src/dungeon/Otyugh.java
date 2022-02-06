package dungeon;

class Otyugh {
  private final int[] location;
  private int health;

  public Otyugh(int[] location) {
    this.location = location;
    this.health = 100;
  }

  public Otyugh(Otyugh otyugh) {
    this.health = otyugh.health;
    this.location = otyugh.location;
  }

  int[] getLocation() {
    return this.location;
  }

  int getHealth() {
    return this.health;
  }

  void setHealth() {
    this.health -= 50;
  }
}
