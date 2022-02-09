import React from "react";
import { useSelector } from "react-redux";
import { Redirect } from "react-router";
import { NavLink } from "react-router-dom";
import { LoaderSvg } from "../../UI/LoaderSvg";

export const Themes = ({ currentTag, setCurrentTag, isLoading }) => {
  const tags = useSelector((state) => state.posts.tags);

  const test = (tag) => {
    setCurrentTag(tag);
  };

  return (
    <div className="themes">
      <NavLink to="/articles">
        <div className="themes__title" onClick={() => setCurrentTag("")}>
          Темы
        </div>
      </NavLink>

      <div className="themes__line"></div>
      <ul className="themes__taggsList">
        {isLoading ? (
          <LoaderSvg />
        ) : (
          tags &&
          tags.map((tag, index) => (
            <NavLink to="/articles" className="navlink">
              <li onClick={() => test({ tag })} key={`${tag}_${index}`}>
                {tag}
              </li>
            </NavLink>
          ))
        )}
      </ul>
    </div>
  );
};
