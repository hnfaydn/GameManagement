package com.gameManagement.demo.core.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper forDto();

    ModelMapper forRequest();
}