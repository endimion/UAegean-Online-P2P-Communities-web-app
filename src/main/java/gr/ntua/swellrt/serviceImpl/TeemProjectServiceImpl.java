/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.serviceImpl;

import gr.ntua.swellrt.model.dao.TeemProjectsRepo;
import gr.ntua.swellrt.model.dmo.TeemProject;
import gr.ntua.swellrt.service.TeemProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class TeemProjectServiceImpl implements TeemProjectService{

    @Autowired
    private TeemProjectsRepo repo;
    
    @Override
    public TeemProject findByWave_id(String waveId) {
        return repo.findByWaveId(waveId);
    }
    
}
