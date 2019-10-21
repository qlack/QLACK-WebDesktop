package com.eurodyn.qlack.webdesktop.mapper;

import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.model.WdApplication;
import org.mapstruct.Mapper;

/**
 * Mapping interface for <tt>WdApplication</tt> entities and DTOs
 *
 * @author European Dynamics SA.
 */
@Mapper(componentModel = "spring")
public interface WdApplicationMapper extends WdMapper<WdApplication, WdApplicationDTO> {

}
