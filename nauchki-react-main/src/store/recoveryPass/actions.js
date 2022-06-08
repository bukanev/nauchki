import { RecoveryPassAPI } from "../../api/api";

export const SET_RECOVERY_PASS_LOADING = 'RECOVERY_PASS::SET_RECOVERY_PASS_LOADING';
export const SET_RECOVERY_PASS_DATA = 'RECOVERY_PASS::SET_RECOVERY_PASS_DATA';
export const SET_RECOVERY_PASS_ERROR = 'RECOVERY_PASS::SET_RECOVERY_PASS_ERROR';

export const setRecoveryPassLoading = () => ({
  type: SET_RECOVERY_PASS_LOADING,
});

export const setRecoveryPassData = (data) => ({
  type: SET_RECOVERY_PASS_DATA,
  payload: data,
});

export const setRecoveryPassError = (error) => ({
  type: SET_RECOVERY_PASS_ERROR,
  payload: error,
});

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