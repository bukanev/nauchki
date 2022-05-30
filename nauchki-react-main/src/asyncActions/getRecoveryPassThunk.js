import { RecoveryPassAPI } from '../api/api';

export const getRecoveryPassThunk =  ({ email }) => {
  return async (dispatch) => {
    try {
      dispatch({ type: 'SET_LOADING' });
      const data = await RecoveryPassAPI.recoveryPass(email);
      dispatch({ type: 'SET_DATA', payload: data });
    } catch (error) {
      dispatch({ type: 'SET_ERROR', payload: error });
    }
  };
};
