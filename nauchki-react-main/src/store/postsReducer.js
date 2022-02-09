export const GET_POSTS = "GET_POSTS";
export const GET_TAGS = "GET_TAGS";

const initialState = {
  posts: [],
  tags: []
};

export const postsReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_POSTS:
      return {
        ...state,
        posts: action.payload
      };
    case GET_TAGS:
      return {
        ...state,
        tags: action.payload,
      };

    default:
      return state;
  }
};

export const getPostsAC = (payload) => ({ type: GET_POSTS, payload });
export const getTagsAC = (payload) => ({ type: GET_TAGS, payload });