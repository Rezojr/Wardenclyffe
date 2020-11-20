package com.wardenclyffe.wardenclyffe.comment;

import com.wardenclyffe.wardenclyffe.common.mapper.AbstractMapper;
import com.wardenclyffe.wardenclyffe.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface CommentMapper extends AbstractMapper<Comment, CommentDto> {
}
