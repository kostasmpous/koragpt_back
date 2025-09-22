package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.ModelsAI;
import com.koragpt.koragpt.models.dtos.Responses.ModelsExtractDTO;
import com.koragpt.koragpt.models.dtos.chat.AllChatDTO;
import com.koragpt.koragpt.repositories.ModelsAIRepository;
import com.koragpt.koragpt.services.ModelsAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/modelsai")
public class ModelsAIController {
    private final ModelsAIRepository modelsAIRepository;
    private final ModelsAIService modelsaiService;

    public ModelsAIController(ModelsAIRepository modelsAIRepository, ModelsAIService chatService, ModelsAIService modelsaiService) {
        this.modelsAIRepository = modelsAIRepository;
        this.modelsaiService = modelsaiService;
    }

    @GetMapping("/{company}")
    public ResponseEntity<List<ModelsExtractDTO>> getAllModelsOfCompany(@PathVariable String company){
        List<ModelsAI> listModels = modelsAIRepository.findAllByCompany(company);

        return ResponseEntity.ok(modelsaiService.prepareModelsList(listModels));
    }
}
