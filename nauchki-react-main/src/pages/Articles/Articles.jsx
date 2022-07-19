import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Articles as ArticlesComponent } from '../../components/Articles/Articles';
import { useParams } from "react-router-dom";
import { getAllPostsThunk, getPostThunk, getTagsThunk } from "../../store/posts/actions";
import { selectPosts, selectTags, selectAllPosts } from "../../store/posts/selectors";

export const Articles = () => {

  const dispatch = useDispatch();

  const params = useParams();

  const posts = useSelector(selectPosts);
  const tags = useSelector(selectTags);
  const allPosts = useSelector(selectAllPosts);

  const [currentTag, setCurrentTag] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [searchingPostTitle, setSearchingPostTitle] = useState('');

  let searchingPost = allPosts.find(el => el.title == searchingPostTitle);

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      await dispatch(getPostThunk());
      await dispatch(getTagsThunk());
      await dispatch(getAllPostsThunk());
      setIsLoading(false);
    })();
  }, []);

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      await dispatch(getPostThunk(currentTag.tag));
      setIsLoading(false);
    })();
  }, [currentTag]);

  return (
    <ArticlesComponent
      posts={posts}
      tags={tags}
      isLoading={isLoading}
      setCurrentTag={setCurrentTag}
      searchingPost={searchingPost}
      setSearchingPostTitle={setSearchingPostTitle}
      searchingPostTitle={searchingPostTitle}
      params={params}
    />
  );
};
