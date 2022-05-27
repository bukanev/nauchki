const initialState = {
  loading: false,
  data: null,
  error: null,
};

export function ResetPassReducer(state = initialState, action) {
  switch (action.type) {
    case 'SET_LOADING':
      console.log(state);
      return { ...state, loading: true };
    case 'SET_DATA':
      return { ...state, loading: false, data: action.payload };
    case 'SET_ERROR':
      return { ...state, loading: false, data: null, error: action.payload };
    default:
      return state;
  }
}

export const getResetPassData = (state) => state.resetPass;
