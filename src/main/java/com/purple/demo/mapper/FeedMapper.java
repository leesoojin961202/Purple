package com.purple.demo.mapper;

import java.util.List;

import com.purple.demo.model.FeedListDTO;
import com.purple.demo.model.FeedWriteDTO;
import com.purple.demo.model.HashtagEntity;
import com.purple.demo.model.MediaEntity;
import com.purple.demo.model.DTO.FeedFavoriteDTO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {
   List<FeedListDTO> selFeedList(FeedListDTO param);
   List<MediaEntity> selMediaList(FeedListDTO param);
   List<HashtagEntity> selHashtagList(FeedListDTO param);
   int isFavorite(int feed_pk, int user_pk);
   int isBookmark(FeedListDTO param);

   FeedFavoriteDTO feedFavorite(FeedFavoriteDTO dto);
   int insFavorite(FeedFavoriteDTO dto);
   int delFavorite(FeedFavoriteDTO dto);
   int favoriteCount(FeedFavoriteDTO dto);

   int insfeed(FeedWriteDTO dto);
   int insfeedimg(List<MediaEntity> p);
}
