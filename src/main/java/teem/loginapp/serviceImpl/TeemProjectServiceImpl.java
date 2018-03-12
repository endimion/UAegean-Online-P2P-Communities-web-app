/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teem.loginapp.model.dao.TeemProjectsRepo;
import teem.loginapp.model.dmo.TeemProject;
import teem.loginapp.service.TeemProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teem.loginapp.service.SwellrtAccountService;

/**
 *
 * @author nikos
 */
@Service
public class TeemProjectServiceImpl implements TeemProjectService {

    private final Logger LOG = LoggerFactory.getLogger(TeemProjectServiceImpl.class);

    @Autowired
    private TeemProjectsRepo repo;

    @Autowired
    private SwellrtAccountService accountService;

    @Override
    public TeemProject findByWave_id(String waveId) {
        return repo.findByWaveId(waveId);
    }

    @Override
    public List<String> getParticipantsIfPromoter(String waveId, String promoter) {
        TeemProject proj = repo.findByWaveId(waveId);
        LOG.info("will serach " + waveId + " promoter " + promoter);
        if (proj != null) {
            LOG.info(proj.getWaveId());
            if (proj.getRoot() != null) {
                LOG.info(proj.getRoot().getPromoter());
            }
        }
        if (proj != null && proj.getRoot().getPromoter().equals(promoter)) {
            return proj.getParticipants().stream()
                    .map(id -> {
                        LOG.info("Searching by ID " + id);
                        return accountService.findById(id);
                    }).filter(account -> {
                return account != null && account.getHuman() != null
                        && !StringUtils.isEmpty(account.getHuman().getEmail());
            }).map(account -> {
                LOG.info("Email  " + account.getHuman().getEmail());
                return account.getHuman().getEmail();
            }).collect(Collectors.toList());

        } else {
            return null;
        }
    }

}
