package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

  private Cowsay() { }
public class Cowsay {
  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay"; // Avoid concatenating user input directly to command
    // Use a logger instead of System.out for better logging control
    processBuilder.command("/usr/games/cowsay", input);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      // Log the exception properly instead of printing stack trace
    }
    return output.toString();
  }
}
