import {
  SET_RECOVERY_PASS_DATA,
  SET_RECOVERY_PASS_ERROR,
  SET_RECOVERY_PASS_LOADING,
} from './actions';

const initialState = {
  loading: false,
  error: null,
  data: null,
};

export function recoveryPassReducer(state = initialState, action) {
  switch (action.type) {
    case SET_RECOVERY_PASS_LOADING:
      return { ...state, loading: true };
    case SET_RECOVERY_PASS_DATA:
      return { ...state, loading: false, error: '', data: action.payload };
    case SET_RECOVERY_PASS_ERROR:
      return { ...state, loading: false, error: action.payload };
    default:
      console.log(state);
      return state;
  }
}

