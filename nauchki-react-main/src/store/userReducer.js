export const GET_USER_DATA = "GET_USER_DATA";
export const TOGGLE_AUTH = "TOGGLE_AUTH";

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

export const getUserDataAC = (payload) => ({ type: GET_USER_DATA, payload });
export const toggleAuthAC = (payload) => ({ type: TOGGLE_AUTH, payload });
