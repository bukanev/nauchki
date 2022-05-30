const initialState = {
  loading: false,
  data: null,
  error: null,
};

export function ResetPassReducer(state = initialState, action) {
  switch (action.type) {
    case 'SET_RESET_PASS_LOADING':
      console.log(state);
      return { ...state, loading: true };
    case 'SET_RESET_PASS_DATA':
      return { ...state, loading: false, data: action.payload };
    case 'SET_RESET_PASS_ERROR':
      return { ...state, loading: false, data: null, error: action.payload };
    default:
      console.log(state);
      return state;
  }
}

export const getResetPassData = (state) => state.resetPass;
