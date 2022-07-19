import React from "react";
import "./avatar.scss";

export const AvatarImg = ({ imgPath }) => {
  return (
    <div
      className="avatar"
      style={{
        backgroundImage: ` url( ${
          imgPath ||
          "https://github.com/bukanev/nauchki/blob/main/nauchki-react-main/src/img/childCardPlaceholder.jpg?raw=true"
        })`,
      }}
    ></div>
  );
};
