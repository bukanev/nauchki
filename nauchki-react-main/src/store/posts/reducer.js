import { GET_POSTS, GET_TAGS } from "./actions";

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