package enshud.s1.lexer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Lexer {
    private final StateMachine stateMachine;
    private final TokenProcessor tokenProcessor;
    private String outputFile;

    public Lexer() {
        TokenRepository tokenRepository = new TokenRepository();
        this.tokenProcessor = new TokenProcessor(tokenRepository);
        this.stateMachine = new StateMachine(tokenProcessor);
    }

    public static void main(final String[] args) {
        System.out.println(new Lexer().run("data/pas/Synerr02.pas", "tmp/out.ts"));
    }

    public String run(final String inputFileName, final String outputFileName) {
        this.outputFile = outputFileName;
        try {
            List<String> buffer = Files.readAllLines(Paths.get(inputFileName));
            processBuffer(buffer);
            writeFile();
            return "OK";
        } catch (IOException e) {
            return "File not found";
        }
    }

    private void processBuffer(List<String> buffer) {
        for (int i = 0; i < buffer.size(); i++) {
            String line = buffer.get(i);
            if (!line.isEmpty()) {
                for (int j = 0; j < line.length(); j++) {
                    stateMachine.transition(line.charAt(j), i + 1);
                }
            }
            stateMachine.endOfLine(i + 1);
        }
    }

    private void writeFile() {
        try {
            if (Files.notExists(Paths.get(outputFile))) {
                Files.createFile(Paths.get(outputFile));
            }
            Files.write(Paths.get(outputFile), tokenProcessor.getTokenList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}