import { ADD_POST, DELETE_POST, GET_POSTS, GET_TAGS } from './actions';

const initialState = {
  posts: [],
  tags: [],
};

export const postsReducer = (state = initialState, { type, payload }) => {
  switch (type) {
    case GET_POSTS:
      return {
        ...state,
        posts: payload,
      };
    case GET_TAGS:
      return {
        ...state,
        tags: payload,
      };
    case ADD_POST:
      return {
        ...state,
        ...payload,
      };
    case DELETE_POST:
      return state.filter(({ id }) => id !== payload);
    default:
      return state;
  }
};
