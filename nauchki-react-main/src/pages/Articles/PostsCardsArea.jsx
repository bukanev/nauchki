import React from "react";
import { LoaderArticle } from "../../UI/LoaderArticle";
import { PostItem } from "./PostItem";

export const PostsCardsArea = ({ posts, isLoading }) => {
  return (
    <div className="acticles__cards-wrapper">
      {isLoading
        ? Array(10)
            .fill()
            .map((_, index) => <LoaderArticle key={index} />)
        : posts && posts.map((post) => <PostItem post={post} key={post.id} />)}
    </div>
  );
};
