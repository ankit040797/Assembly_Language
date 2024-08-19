package service;

import Repository.ProgramResultRepository;
import model.ProgramResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AssemblyService {
    @Autowired
    private ProgramResultRepository repository;

    private static final Map<String, Integer> registers = new HashMap<>();

    public String executeProgram(String programText) {
        // Initialize registers
        registers.clear();
        registers.put("REG1", 0);
        registers.put("REG2", 0);

        // Execute the assembly program
        StringBuilder output = new StringBuilder();
        String[] program = programText.split("\n");
        for (String instruction : program) {
            if (instruction.startsWith("MV")) {
                mv(instruction);
            } else if (instruction.startsWith("ADD")) {
                add(instruction);
            } else if (instruction.startsWith("SHOW")) {
                output.append(show(instruction)).append("\n");
            } else {
                output.append("Invalid instruction: ").append(instruction).append("\n");
            }
        }

        // Persist the result
        ProgramResult result = new ProgramResult();
        result.setProgramText(programText);
        result.setResult(output.toString());
        repository.save(result);

        return output.toString();
    }

    private void mv(String instruction) {
        String[] parts = instruction.split(",");
        String register = parts[0].split(" ")[1].trim();
        int value = Integer.parseInt(parts[1].split("#")[1].trim());
        registers.put(register, value);
    }

    private void add(String instruction) {
        String[] parts = instruction.split(",");
        String register = parts[0].split(" ")[1].trim();
        String operand = parts[1].trim();
        int registerValue = registers.getOrDefault(register, 0);
        int value = operand.startsWith("#") ? Integer.parseInt(operand.substring(1))
                : registers.getOrDefault(operand, 0);
        registers.put(register, registerValue + value);
    }

    private String show(String instruction) {
        String register = instruction.split(" ")[1].trim();
        return "Value in " + register + ": " + registers.get(register);
    }

}
