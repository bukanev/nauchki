import React from "react";
import { NavLink } from "react-router-dom";

export const PostItem = ({ post }) => {
  return (
    <NavLink to={`/articles/${post.id}`}>
    <div>
      <div className="post__card ">
        {/* <p>#{post.tag}</p>
        <p>{post.text}</p> */}
        {post.img && (
          <img className="post__card-img" src={post.img} alt="Картинка" />
        )}
      </div>
      <p className="post__card-title">{post.title ? post.title : 'Нет названия'}</p> 
    </div>
    </NavLink> 
  );
};