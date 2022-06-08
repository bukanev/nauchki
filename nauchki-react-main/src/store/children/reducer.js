import { GET_CHILDREN } from "./actions";

const initialState = {
  children: [],
};

export const childrenReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_CHILDREN:
      return {
        ...state,
        children: action.payload,
      };
    default:
      return state;
  }
};