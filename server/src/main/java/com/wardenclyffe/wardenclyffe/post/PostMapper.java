package com.wardenclyffe.wardenclyffe.post;

import com.wardenclyffe.wardenclyffe.common.mapper.AbstractMapper;
import com.wardenclyffe.wardenclyffe.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface PostMapper extends AbstractMapper<Post, PostDto> {
}
