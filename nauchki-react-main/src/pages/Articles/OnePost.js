import React from "react";
import { useSelector } from "react-redux";
import { withRouter } from "react-router-dom";
import { useHistory } from "react-router";

const OnePostWithoutRouter = (props) => {
  const posts = useSelector((state) => state.posts.posts);
  const filteredPosts = posts.filter(
    (item) => item.id.toString() === props.match.params.id
  );

  let history = useHistory();
  const handleBack = () => {
    history.push("/articles");
  };

  return (
    <div className="onePost-wrapper">
      {filteredPosts && filteredPosts.map((post) => (
        <div key={post.id}>
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
          <h1 className="onePost-title">{filteredPosts.title !== null ? post.title : 'Нет названия'}</h1>

          <div className="onePost-imgWrapper">
            <img className="onePost-img" src={post.img} alt="Картинка" />
          </div>
          <div className="onePost-textWrapper">
            <p>{post.text}</p>
          </div>
          <br />
        </div>
      ))}
    </div>
  );
};

export const OnePost = withRouter(OnePostWithoutRouter);
/* 
  withRouter помещает в props history, location, match; 
  в math содержатся params, в котрые мы поместили id из урла (см Articles). 

  !!! пост оборачивается в NavLink в PostItem, ссылка попадет в url по клику, 
      затем Route в Arcticles следит за изменениями url и перерисовывает
*/
