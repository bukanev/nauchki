import './openPost.scss';

export function OnePost ({post, handleBack}) {
    return (
        <div className="onePost-wrapper">
          {
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
    
              <h1 className="onePost-title">{post.title}</h1>
              <h3 className="onePost-title">{post.subtitle}</h3>
              <p>{post.tag}</p>
              {
                post.images.length > 0 &&
                <div className="onePost-imgWrapper">
                  <img className="onePost-img" src={post.images[0]?.externalPath} alt="Картинка" />
                </div>
              }
    
              <div className="onePost-textWrapper">
                <p>{post.text}</p>
              </div>
            </div>
          }
        </div>
      );
}
