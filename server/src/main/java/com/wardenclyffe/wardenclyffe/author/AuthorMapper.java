package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.common.mapper.AbstractMapper;
import com.wardenclyffe.wardenclyffe.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface AuthorMapper extends AbstractMapper<Author, AuthorDto> {
}
