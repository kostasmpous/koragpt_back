package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.ModelsAI;
import com.koragpt.koragpt.models.dtos.Responses.ModelsExtractDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelsAIService {
    public List<ModelsExtractDTO> prepareModelsList(List<ModelsAI> list){
        List<ModelsExtractDTO> result = new ArrayList<>();
        for(ModelsAI model:list){
            ModelsExtractDTO m = new ModelsExtractDTO();
            m.setModel(model.getModel().toString());
            m.setDisplay_name(model.getName());
            result.add(m);
        }
        return result;
    }
}
