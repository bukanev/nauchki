import {
  ERROR_AUTH,
  GET_USER_DATA,
  TOGGLE_AUTH,
} from './actions';

const initialState = {
  isAuth: false,
  data: null,
  error: null,
};

export const userReducer = (state = initialState, { type, payload }) => {
  switch (type) {
    case TOGGLE_AUTH:
      return { ...state, isAuth: payload };
    case GET_USER_DATA:
      return { ...state, data: payload };
    case ERROR_AUTH:
      return { ...state, error: payload };
    default:
      console.log(state);
      return state;
  }
};
