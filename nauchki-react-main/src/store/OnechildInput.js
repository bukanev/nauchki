export const GET_PHRASE = "GET_PHRASE";

const childrenPh = {
  phase: [],
};

export const childrenInputReducer = (state = childrenPh, action) => {
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

export const getchildrenInputAC = (payload) => ({ type: GET_PHRASE, payload });
