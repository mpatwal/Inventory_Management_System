package com.mona.inventoryms.services;

import com.mona.inventoryms.models.CommonObject;
import com.mona.inventoryms.repository.CommonObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonObjectService {

    private CommonObjectRepository commonObjectRepository;

    @Autowired
    public CommonObjectService(CommonObjectRepository commonObjectRepository) {
        this.commonObjectRepository = commonObjectRepository;
    }

    public List<CommonObject> getAllCommonObjects(){
        return commonObjectRepository.findAll();
    }

    public List<CommonObject> findByObjectType(String objectType) {
        return commonObjectRepository.findByObjectType(objectType);
    }

    public CommonObject getCommonObjectById(Long id) {
        return commonObjectRepository.findById(id).orElse(null);
    }

    public CommonObject save(CommonObject commonObject) {
        return commonObjectRepository.save(commonObject);
    }

    public void deleteCommonObject(Long id){
        commonObjectRepository.deleteById(id);
    }

}
