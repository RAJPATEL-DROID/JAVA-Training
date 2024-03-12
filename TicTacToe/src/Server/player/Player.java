package Server.player;

import Server.gameboard.Seed;

import java.io.BufferedReader;
import java.io.PrintWriter;

public record Player(BufferedReader reader, PrintWriter writer, Seed seed)
{}