const initialState = {
  loading: false,
  error: null,
  data: null,
};

export function recoveryPassReducer(state = initialState, action) {
  switch (action.type) {
    case 'SET_LOADING':
      return { ...state, loading: true };
    case 'SET_ERROR':
      return { ...state, loading: false, error: action.payload };
    case 'SET_DATA':
      return { ...state, loading: false, error: '', data: action.payload };
    default:
      return state;
  }
}

export const getRecoveryPassData = (state) => state.recoveryPass;
