/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dao;

import teem.loginapp.model.dmo.TeemProject;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author nikos
 */
public interface TeemProjectsRepo extends MongoRepository<TeemProject,Long>{
    public TeemProject findByWaveId(String waveId);
    
}
