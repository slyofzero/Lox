package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
  static boolean hadError = false;

  private static void runFile(String file) {
    try {
      byte[] bytes = Files.readAllBytes(Paths.get(file));
      String source = new String(bytes, Charset.defaultCharset());
      run(source);
      if (hadError) System.exit(65);
    } catch (IOException e) {
      System.out.println("Couldn't read the lox input input.");
    }
  }

  private static void runPrompt() {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    try {
      for (;;) {
        System.out.print("> ");
        String line = reader.readLine();
        if (line == null) break;
        run(line);
        hadError = false;
      }
    } catch (IOException e) {
      System.out.println("Couldn't read the lox input.");
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    for (Token token: tokens) {
      System.out.println(token);
    }
  }

  static void error(int line, String message) {
    report(line, "", message);
  }

  static void report(int line, String where, String message) {
    System.err.println("[line " + line + "] Error " + where + ": " + message);
  }

  public static void main(String[] args) {
    if (args.length > 1) {
      System.err.println("Usage: jlox [script]");
      System.exit(64);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }
}
