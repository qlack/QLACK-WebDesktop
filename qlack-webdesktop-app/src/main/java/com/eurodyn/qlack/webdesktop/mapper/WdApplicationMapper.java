package com.eurodyn.qlack.webdesktop.mapper;

import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.model.WdApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapping interface for <tt>WdApplication</tt> entities and DTOs
 *
 * @author European Dynamics SA.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WdApplicationMapper extends WdMapper<WdApplication, WdApplicationDTO> {

  @Override
  @Mapping(source = "id", target = "id")
  @Mapping(source = "checksum", target = "checksum", ignore = true)
  WdApplicationDTO mapToDTO(WdApplication entity);
}
