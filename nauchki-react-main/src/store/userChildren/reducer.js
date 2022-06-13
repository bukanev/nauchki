import { SET_USER_CHILDREN_DATA, SET_USER_CHILDREN_ERROR, SET_USER_CHILDREN_LOADING } from "./actions";

const initialState = {
  loading: false,
  data: null,
  error: null,
};

export const UserChildrenReducer = (state = initialState, action) => {
  switch (action.type) {
    case SET_USER_CHILDREN_LOADING:
      return { ...state, loading: true };
    case SET_USER_CHILDREN_DATA:
      return { ...state, loading: false, data: action.payload };
    case SET_USER_CHILDREN_ERROR:
      return { ...state, loading: false, data: null, error: action.payload };
    default:
      return state;
  }
};

