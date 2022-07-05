import React from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { selectPosts } from "../../store/posts/selectors";

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

  return (
    <div className="onePost-wrapper">
      {
        filteredPosts &&
        <div key={filteredPosts[0].id}>
          <button className="onePost-arrowBtn" onClick={handleBack}>
            <svg
              width="53"
              height="54"
              viewBox="0 0 53 54"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M34.3066 46.0913L15.5688 27.0001L34.3066 7.90881L37.4313 11.0903L21.814 27.0001L37.4313 42.9098L34.3066 46.0913Z"
                fill="#0EAC99"
              />
            </svg>
          </button>

          <h1 className="onePost-title">{filteredPosts[0].title}</h1>
          <h3 className="onePost-title">{filteredPosts[0].subtitle}</h3>
          <p>{filteredPosts[0].tag}</p>
          {
            filteredPosts[0].images.length > 0 &&
            <div className="onePost-imgWrapper">
              <img className="onePost-img" src={filteredPosts[0].images[0].externalPath} alt="Картинка" />
            </div>
          }

          <div className="onePost-textWrapper">
            <p>{filteredPosts[0].text}</p>
          </div>
        </div>
      }
    </div>
  );
};
