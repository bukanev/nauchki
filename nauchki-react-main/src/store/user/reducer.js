import { GET_USER_DATA, TOGGLE_AUTH } from './actions';

const initialState = {
  userData: {},
  isAuth: false,
};

export const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_USER_DATA:
      return {
        ...state,
        userData: action.payload,
      };
    case TOGGLE_AUTH:
      return {
        ...state,
        isAuth: action.payload,
      };
    default:
      return state;
  }
};