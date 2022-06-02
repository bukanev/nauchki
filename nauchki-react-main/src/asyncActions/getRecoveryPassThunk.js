import { RecoveryPassAPI } from '../api/api';
import {
  setRecoveryPassData,
  setRecoveryPassError,
  setRecoveryPassLoading,
} from '../store/recoveryPass/actions';

export const getRecoveryPassThunk = ({ email }) => {
  return async (dispatch) => {
    try {
      dispatch(setRecoveryPassLoading());

      const data = await RecoveryPassAPI.recoveryPass(email);
      
      dispatch(setRecoveryPassData(data));
    } catch (error) {
      dispatch(setRecoveryPassError(error));
    }
  };
};
