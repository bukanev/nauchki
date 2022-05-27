import { ResetPassAPI } from '../api/api';

export const getResetPassThunk = ({ resetPasswordCode, password }) => {
  return async (dispatch) => {
    try {
      console.log(resetPasswordCode, password);
      dispatch({ type: 'SET_LOADING' });
      const data = await ResetPassAPI.resetPass(resetPasswordCode, password);
      dispatch({ type: 'SET_DATA', payload: data });
    } catch (error) {
      dispatch({ type: 'SET_ERROR', payload: error });
    }
  };
};