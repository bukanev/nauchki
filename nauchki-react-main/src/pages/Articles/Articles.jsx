import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";

import { getPostThunk, getTagsThunk } from "../../store/posts/actions";
import { selectPosts } from "../../store/posts/selectors";

import { Themes } from "./Themes";
import { PostItem } from "./PostItem";

export const Articles = () => {
  const dispatch = useDispatch();

  const posts = useSelector(selectPosts);

  const [currentTag, setCurrentTag] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      await dispatch(getPostThunk());
      await dispatch(getTagsThunk());
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
    <div className="articles">
      <div className="acticles__container">
        <Themes currentTag={currentTag} setCurrentTag={setCurrentTag} isLoading={isLoading} />
        <div>
          <h1 className="articles__title">Интересные статьи</h1>
          <input
            className="acrticles__search"
            placeholder="Поиск по статьям"
          ></input>
          <div className="acticles__cards">

            <div className="acticles__cards-wrapper">
              {posts && posts.map((post) =>
                <Link to="post.id">
                  <PostItem post={post} key={post.id} />
                </Link>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
