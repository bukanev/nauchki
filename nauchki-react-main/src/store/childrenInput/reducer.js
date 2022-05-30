// export const GET_PHRASE = 'GET_PHRASE';
import { GET_PHRASE } from "./actions";

const initialState = {
  phase: [],
};

export const childrenInputReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_PHRASE:
      return {
        ...state,
        phase: action.payload,
      };
    default:
      return state;
  }
};

// export const getchildrenInputAC = (payload) => ({ type: GET_PHRASE, payload });
