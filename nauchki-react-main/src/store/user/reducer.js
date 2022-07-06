import { ADD_IMAGES, ERROR_AUTH, GET_USER_DATA, TOGGLE_AUTH } from './actions';

const initialState = {
  isAuth: false,
  data: [],
  error: null,
};

export const userReducer = (state = initialState, { type, payload }) => {
  switch (type) {
    case TOGGLE_AUTH:
      return { ...state, isAuth: payload };
    case GET_USER_DATA:
      return { ...state, error: null, data: payload };
    case ERROR_AUTH:
      return { ...state, isAuth: false, data: null, error: payload };
    case ADD_IMAGES:
      console.log(state.data.images);

      return {
        ...state,
        data: {
          ...state.data,
          images: [...state.data.images, payload],
        },
      };
    // return state.data.images.push(payload);
    default:
      return state;
  }
};
