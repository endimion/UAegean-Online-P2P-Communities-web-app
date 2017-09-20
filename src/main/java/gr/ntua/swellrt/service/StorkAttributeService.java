/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.service;

import gr.ntua.swellrt.model.dmo.StorkAttributesDMO;
import gr.ntua.swellrt.model.dmo.StrokAttributesMongoDMO;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface StorkAttributeService {

    public StorkAttributesDMO findByName(String name);

    public List<StorkAttributesDMO> getEnabled();

    public List<StrokAttributesMongoDMO> getEnabledMng();

    public StrokAttributesMongoDMO save(StrokAttributesMongoDMO dmo);

    public StrokAttributesMongoDMO findByNameMng(String name);

    public StrokAttributesMongoDMO findByEiDASNameMng(String name);
}
