import { ResetPassAPI } from '../api/api';
import {
  setResetPassData,
  setResetPassError,
  setResetPassLoading,
} from '../store/resetPass/actions';

export const getResetPassThunk = ({ resetPasswordCode, password }) => {
  return async (dispatch) => {
    try {
      dispatch(setResetPassLoading());

      const data = await ResetPassAPI.resetPass(resetPasswordCode, password);

      dispatch(setResetPassData(data));
    } catch (error) {
      dispatch(setResetPassError);
    }
  };
};
