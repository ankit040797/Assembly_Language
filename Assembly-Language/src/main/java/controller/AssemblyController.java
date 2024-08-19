package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AssemblyService;

@RestController
@RequestMapping("/assembly")
public class AssemblyController {
    @Autowired
    private AssemblyService assemblyService;

    @PostMapping("/execute")
    public String executeProgram(@RequestBody String programText) {
        return assemblyService.executeProgram(programText);
    }
}
