import { ResetPassAPI } from '../api/api';
import {
  SET_RESET_PASS_DATA,
  SET_RESET_PASS_ERROR,
  SET_RESET_PASS_LOADING,
} from '../store/resetPass/actions';

export const getResetPassThunk = ({ resetPasswordCode, password }) => {
  return async (dispatch) => {
    try {
      console.log(resetPasswordCode, password);
      dispatch({ type: SET_RESET_PASS_LOADING });
      const data = await ResetPassAPI.resetPass(resetPasswordCode, password);
      dispatch({ type: SET_RESET_PASS_DATA, payload: data });
    } catch (error) {
      dispatch({ type: SET_RESET_PASS_ERROR, payload: error });
    }
  };
};
