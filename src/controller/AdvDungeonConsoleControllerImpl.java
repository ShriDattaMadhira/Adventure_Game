package controller;

import dungeon.Direction;
import dungeon.Dungeon;

import java.io.IOException;
import java.util.Scanner;

/**
 * The controller that handles the inputs and tells model what to do and when.
 */
public class AdvDungeonConsoleControllerImpl implements AdvDungeonConsoleController {
  private final Appendable out;
  private final Readable in;
  private final Dungeon d;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public AdvDungeonConsoleControllerImpl(Readable in, Appendable out, Dungeon dungeon) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon cannot be null");
    }
    this.out = out;
    this.in = in;
    this.d = dungeon;
  }

  private Direction getDirection(String d) {
    switch (d) {
      case "north":
        return Direction.NORTH;
      case "south":
        return Direction.SOUTH;
      case "east":
        return Direction.EAST;
      case "west":
        return Direction.WEST;
      default:
        throw new IllegalArgumentException("---Direction can only be North/South/East/West.---");
    }
  }

  @Override
  public void playGame() {
    Scanner scan = new Scanner(this.in);

    String action;
    String direction;
    int distance;
    boolean pickTreasure;
    boolean pickArrows;
    try {
      while (!d.isGameOver()) {
        out.append("\n").append(d.getGameDetails()).append("\n").append(d.getPlayerDetails());
        out.append("What action you want to choose? Move(M), Shoot an arrow(S), Pick items(P) - ");
        try {
          if (scan.hasNext()) {
            action = scan.next();
            out.append(action).append("\n");
            if (action.equalsIgnoreCase("M")) {
              out.append("Which direction? ");
              direction = scan.next().toLowerCase();
              out.append(direction).append("\n");
              d.move(getDirection(direction));
            } else if (action.equalsIgnoreCase("P")) {
              out.append("Do you want to pick up treasure?[1/0] ");
              pickTreasure = Integer.parseInt(scan.next()) == 1;
              out.append(Boolean.toString(pickTreasure)).append("\n");
              out.append("Do you want to pick up arrows?[1/0] ");
              pickArrows = Integer.parseInt(scan.next()) == 1;
              out.append(Boolean.toString(pickArrows)).append("\n");
              d.pick(pickTreasure, pickArrows);
            } else if (action.equalsIgnoreCase("S")) {
              try {
                out.append("Which direction? ");
                direction = scan.next().toLowerCase();
                out.append(direction).append("\n");
                out.append("How far do you want the shoot the arrow? ");
                try {
                  distance = Integer.parseInt(scan.next());
                  out.append(Integer.toString(distance)).append("\n");
                  d.shoot(getDirection(direction), distance);
                } catch (NumberFormatException nfe) {
                  out.append("---").append("Give numerical digits").append("---\n");
                }
              } catch (IllegalArgumentException iae) {
                out.append("---").append(iae.getMessage()).append("---\n");
              }
            } else {
              out.append("---Invalid choice of action - ").append(action)
                      .append(". Choose Again.---");
            }
          } else {
            break;
          }
        } catch (IllegalArgumentException iae) {
          out.append("---").append(iae.getMessage()).append("---\n");
        }
      } // end of while loop.
      if (d.isGameOver()) {
        if (d.getEaten()) {
          out.append("\n").append("\nEaten by Otyugh. Game is over!");
        } else {
          out.append("\n").append("\nEnd location reached. Game is over!");
        }
      } else {
        out.append("\nPlayer is not eaten. Player did not reach end location. But input ended.\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  } // end of method.

} // end of class.
