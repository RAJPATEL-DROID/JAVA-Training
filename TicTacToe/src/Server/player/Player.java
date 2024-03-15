package Server.player;

import Server.gameboard.Symbol;

import java.io.BufferedReader;
import java.io.PrintWriter;

public record Player(BufferedReader reader, PrintWriter writer, Symbol symbol)
{}