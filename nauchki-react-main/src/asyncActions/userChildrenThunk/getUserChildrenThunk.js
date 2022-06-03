import { UserChildrenAPI } from '../../api/api';
import {
  setUserChildrenData,
  setUserChildrenError,
  setUserChildrenLoading,
} from '../../store/userChildren/reducer';

export const getuserChildrenThunk = (userId) => {
  return async (dispatch) => {
    try {
      dispatch(setUserChildrenLoading());
      await UserChildrenAPI.getUserChildren(userId).then((res) => {
        dispatch(setUserChildrenData(res.data));
      });
    } catch (error) {
      dispatch(setUserChildrenError(error));
      console.log(error);
    }
  };
};
