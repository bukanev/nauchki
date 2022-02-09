export const GET_CHILDREN = "GET_CHILDREN";

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

export const getChildrenAC = (payload) => ({ type: GET_CHILDREN, payload });
