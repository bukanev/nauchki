import React from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { selectPosts } from "../../store/posts/selectors";
import { Error404 } from "../Error 404/Error404";
import { OnePost as OnePostComponent } from "../../components/Articles/OnePost";

export const OnePost = () => {
  const params = useParams();
  
  const posts = useSelector(selectPosts);

  const filteredPosts = posts.filter(
    (post) => post.id.toString() === params.id && post
  )

  let navigate = useNavigate();

  const handleBack = () => {
    navigate("/articles");
  };

  if (filteredPosts.length > 0) {
    return <OnePostComponent post={filteredPosts[0]} handleBack={handleBack} />
  } 
  return <Error404 />
};
